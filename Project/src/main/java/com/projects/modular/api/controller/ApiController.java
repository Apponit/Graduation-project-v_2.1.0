
package com.projects.modular.api.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.projects.config.web.ResponseData;
import com.projects.config.web.WebProperties;
import com.projects.core.common.exception.BizExceptionEnum;
import com.projects.modular.healthPlatform.entity.Activity;
import com.projects.modular.healthPlatform.entity.ActivityUser;
import com.projects.modular.healthPlatform.entity.Collect;
import com.projects.modular.healthPlatform.entity.Comment;
import com.projects.modular.healthPlatform.entity.HealthyService;
import com.projects.modular.healthPlatform.entity.News;
import com.projects.modular.healthPlatform.entity.Order;
import com.projects.modular.healthPlatform.entity.Question;
import com.projects.modular.healthPlatform.entity.Record;
import com.projects.modular.healthPlatform.entity.RegisteredUsers;
import com.projects.modular.healthPlatform.entity.Type;
import com.projects.modular.healthPlatform.model.params.ActivityUserParam;
import com.projects.modular.healthPlatform.model.params.CollectParam;
import com.projects.modular.healthPlatform.model.params.CommentParam;
import com.projects.modular.healthPlatform.model.params.HealthyServiceParam;
import com.projects.modular.healthPlatform.model.params.LocationParam;
import com.projects.modular.healthPlatform.model.params.OrderParam;
import com.projects.modular.healthPlatform.model.params.QuestionParam;
import com.projects.modular.healthPlatform.model.params.RegisteredUsersParam;
import com.projects.modular.healthPlatform.model.result.ActivityResult;
import com.projects.modular.healthPlatform.model.result.CommentResult;
import com.projects.modular.healthPlatform.model.result.DataModels;
import com.projects.modular.healthPlatform.model.result.NewsResult;
import com.projects.modular.healthPlatform.model.result.OrderResult;
import com.projects.modular.healthPlatform.model.result.QuestionResult;
import com.projects.modular.healthPlatform.model.result.RecordResult;
import com.projects.modular.healthPlatform.service.ActivityService;
import com.projects.modular.healthPlatform.service.ActivityUserService;
import com.projects.modular.healthPlatform.service.CollectService;
import com.projects.modular.healthPlatform.service.CommentService;
import com.projects.modular.healthPlatform.service.HealthyServiceService;
import com.projects.modular.healthPlatform.service.LocationService;
import com.projects.modular.healthPlatform.service.NewsService;
import com.projects.modular.healthPlatform.service.OrderService;
import com.projects.modular.healthPlatform.service.QuestionService;
import com.projects.modular.healthPlatform.service.RecordService;
import com.projects.modular.healthPlatform.service.RegisteredUsersService;
import com.projects.modular.healthPlatform.service.TypeService;
import com.projects.modular.system.entity.Login;
import com.projects.modular.system.entity.User;
import com.projects.modular.system.service.UserService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.MD5Util;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * 小程序接口控制器
 */
@RestController
@Validated
@RequestMapping("/api")
public class ApiController extends BaseController {
    final static int NEIGHBORHOOD_NUM = 10;
    final static int RECOMMENDER_NUM = 5;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private WebProperties gunsProperties;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RegisteredUsersService registeredUsersService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityUserService activityUserService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private HealthyServiceService healthyServiceService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private LocationService locationService;

    /**
     * 用户注册
     *
     * @param registeredUsersParam
     * @return
     */
    @PostMapping(value = "/registerUser")
    public ResponseData registerUser(@RequestBody RegisteredUsersParam registeredUsersParam) {

        try {
            QueryWrapper<RegisteredUsers> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", registeredUsersParam.getUserName());
            RegisteredUsers registerUser = registeredUsersService.getOne(queryWrapper);
            if (null != registerUser) {
                return ResponseData.error("用户已注册");
            }
            String string = MD5Util.encrypt(registeredUsersParam.getPassWord());
            registeredUsersParam.setPassWord(string);
            registeredUsersParam.setType("0");
            registeredUsersService.add(registeredUsersParam);

            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }


    /**
     * 查询新闻列表
     *
     * @param name
     * @param type
     * @return
     */
    @PostMapping(value = "/getNewList")
    public ResponseData getNewList(String name, String type) {

        try {
            QueryWrapper<News> objectQueryWrapper4 = new QueryWrapper<>();
            if (ToolUtil.isNotEmpty(name)) {
                objectQueryWrapper4.like("title", name);
            }
            if (ToolUtil.isNotEmpty(type)) {
                objectQueryWrapper4.eq("type", type);
            }
            List<News> list = newsService.getBaseMapper().selectList(objectQueryWrapper4);


            return ResponseData.success(list);
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }


    /**
     * 保存留言
     *
     * @param questionParam
     * @return
     */
    @PostMapping(value = "/saveMeassage")
    public ResponseData saveMeassage(@RequestBody QuestionParam questionParam) {

        try {
            questionParam.setStatus(0);
            questionParam.setCreateTime(new Date());
            questionService.add(questionParam);
            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }


    /**
     * 查询我的问诊
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/getMessageList")
    public ResponseData getMessageList(Long userId) {
        try {
            QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("pid", 0);
            List<Question> list = questionService.getBaseMapper().selectList(queryWrapper);
            List<QuestionResult> listResults = new ArrayList<>();
            for (Question question : list) {
                QuestionResult result = new QuestionResult();
                ToolUtil.copyProperties(question, result);
                RegisteredUsers registeredUsers = registeredUsersService.getById(question.getUserId());
                if (null != registeredUsers) {
                    result.setHead(registeredUsers.getPic());
                    result.setNickName(registeredUsers.getName());
                }
                listResults.add(result);
            }
            return ResponseData.success(listResults);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


    /***
     * 查看聊天记录
     * @param userId
     * @param questionId
     * @return
     */
    @PostMapping(value = "/getChatList")
    public ResponseData getChatList(Long userId, Long questionId) {

        try {
            QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("question_id", questionId).or().eq("pid", questionId);
            List<Question> list = questionService.getBaseMapper().selectList(queryWrapper);
            List<QuestionResult> listResults = new ArrayList<>();
            for (Question question : list) {
                QuestionResult result = new QuestionResult();
                ToolUtil.copyProperties(question, result);
                if (question.getUserId().toString().equals(userId.toString())) {
                    result.setT(true);
                } else {
                    result.setT(false);
                }
                RegisteredUsers registeredUsers = registeredUsersService.getById(question.getUserId());
                if (null != registeredUsers) {
                    result.setHead(registeredUsers.getPic());
                    result.setNickName(registeredUsers.getName());
                } else {
                    result.setHead("https://img1.baidu.com/it/u=2377473948,2547206030&fm=253&app=138&size=w931");
                    result.setNickName("管理员");
                }
                listResults.add(result);
            }
            return ResponseData.success(listResults);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }

    /**
     * 新闻分类查询
     *
     * @return
     */
    @PostMapping(value = "/getTypeList")
    public ResponseData getTypeList() {

        try {
            QueryWrapper<Type> objectQueryWrapper4 = new QueryWrapper<>();

            List<Type> list = typeService.getBaseMapper().selectList(objectQueryWrapper4);

            return ResponseData.success(list);
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }


    /**
     * 查询健康服务
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/getHealthyServiceList")
    public ResponseData getHealthyServiceList(@RequestBody HealthyServiceParam param) {
        try {
            QueryWrapper<HealthyService> objectQueryWrapper = new QueryWrapper<>();
            if (ToolUtil.isNotEmpty(param.getName())) {
                objectQueryWrapper.like("name", param.getName());
            }
            List<HealthyService> list = healthyServiceService.getBaseMapper().selectList(objectQueryWrapper);

            return ResponseData.success(list);
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }
    }


    /**
     * 查询所有的活动
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/getActivityList")
    public ResponseData getActivityList(String name) {

        try {
            QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
            if (ToolUtil.isNotEmpty(name)) {
                queryWrapper.like("title", name);
            }
            List<Activity> list = activityService.getBaseMapper().selectList(queryWrapper);
            List<ActivityResult> listResult = new ArrayList<>();
            for (Activity activity : list) {
                QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("forum_id", activity.getActivityId());
                Integer count = commentService.getBaseMapper().selectCount(queryWrapper1);

                ActivityResult result = new ActivityResult();
                ToolUtil.copyProperties(activity, result);
                result.setCoount(count);
                listResult.add(result);
            }


            return ResponseData.success(listResult);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }

    /**
     * 查询活动详情信息
     *
     * @param activityId
     * @param userId
     * @return
     */
    @GetMapping(value = "/getActivityInfo")
    public ResponseData getActivityInfo(Long activityId, Long userId) {

        try {
            Activity activity = activityService.getById(activityId);
            ActivityResult result = new ActivityResult();
            ToolUtil.copyProperties(activity, result);
            QueryWrapper<ActivityUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_id", activityId);
            if (null != userId) {
                queryWrapper.eq("user_id", userId);
                ActivityUser activityUser = activityUserService.getOne(queryWrapper);
                if (null != activityUser) {

                    result.setStatus(1);
                }
            }


            QueryWrapper<ActivityUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("activity_id", activityId);
            List<ActivityUser> list = activityUserService.getBaseMapper().selectList(queryWrapper1);
            List<RegisteredUsers> listUser = new ArrayList<>();
            if (null != list) {
                for (ActivityUser activityUser2 : list) {
                    RegisteredUsers registeredUsers = registeredUsersService.getById(activityUser2.getUserId());
                    listUser.add(registeredUsers);
                }
            }

            result.setUser(listUser);

            return ResponseData.success(result);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }

    /**
     * 活动报名
     *
     * @return
     */
    @PostMapping(value = "/enlist")
    public ResponseData enlist(@RequestBody ActivityUserParam activityUserParam) {

        try {

            Activity activity = activityService.getById(activityUserParam.getActivityId());
            if (activity.getEndPoint() == 1) {
                return ResponseData.error("活动已结束");
            }


            QueryWrapper<ActivityUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_id", activityUserParam.getActivityId());
            queryWrapper.eq("user_id", activityUserParam.getUserId());

            ActivityUser activityUser = activityUserService.getOne(queryWrapper);
            if (null != activityUser) {
                activityUserService.removeById(activityUser.getActivityUserId());
            } else {
                activityUserParam.setTime(new Date());
                activityUserService.add(activityUserParam);
            }


            return ResponseData.success();

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }

    /**
     * 获取评论
     *
     * @param forumId
     * @return
     */
    @PostMapping(value = "/getCommentList")
    public ResponseData getCommentList(Long forumId) {

        try {

            CommentParam param = new CommentParam();
            param.setForumId(forumId);
            List<CommentResult> list = commentService.findListBySpec(param);

            return ResponseData.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error(e.getMessage());
        }


    }


    /**
     * 发布评论
     *
     * @param commentParam
     * @return
     */
    @PostMapping(value = "/saveComment")
    public ResponseData saveComment(@RequestBody CommentParam commentParam) {

        try {
            commentParam.setCreateTime(new Date());
            commentService.add(commentParam);
            return ResponseData.success();

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }


    /**
     * 我的预约
     * @param userId
     * @param type
     * @return
     */
    /**
     * @param userId
     * @return
     */
    @GetMapping(value = "/getOrderList")
    public ResponseData getOrderList(Long userId) {

        try {

            OrderParam param = new OrderParam();
            param.setUserId(userId);
            List<OrderResult> list = orderService.findListBySpec(param);


            return ResponseData.success(list);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }


    }

    /**
     * @param userId
     * @return
     */
    @PostMapping(value = "/getRecommendList")
    public ResponseData getRecommendList(Long userId) {


        try {
            List<Long> usertInId = new ArrayList<>();
            List<News> list31 = new ArrayList<>();
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setClassForTemplateLoading(this.getClass(), "/templates");
            Template template = configuration.getTemplate("tuijian.ftl");
            Map<String, Object> resultMap = new HashMap<>();

            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/dataModel.txt");

            file.delete();
            if (!file.exists())
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            Writer out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "utf-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            if (null != userId) {

                //通过用户ID得到收藏的新闻ID
                QueryWrapper<Collect> objectSongWrapper = new QueryWrapper<>();
                objectSongWrapper.eq("user_id", userId).select(new String[]{"news_id"});
                List<Object> newCollection = collectService.getBaseMapper().selectObjs(objectSongWrapper);
                //根据新闻ID找到所有收藏改新闻的用户ID
                if (null != newCollection && !newCollection.isEmpty()) {
                    QueryWrapper<Collect> objectSongWrapper2 = new QueryWrapper<>();
                    objectSongWrapper2.in("news_id", newCollection).select(new String[]{"user_id"});
                    List<Object> list3 = collectService.getBaseMapper().selectObjs(objectSongWrapper2);
                    //根据用户ID找到他们的收藏的新闻的ID
                    if (null != list3 && !list3.isEmpty()) {
                        QueryWrapper<Collect> objectSongWrapper3 = new QueryWrapper<>();
                        objectSongWrapper3.in("user_id", list3);
                        List<Collect> list4 = collectService.getBaseMapper().selectList(objectSongWrapper3);


                        List<DataModels> listDataModel = new ArrayList<>();
                        for (Collect collection : list4) {

                            DataModels data = new DataModels();
                            data.setGoodsId(collection.getNewsId() + "");
                            data.setUserId(collection.getUserId() + "");
                            listDataModel.add(data);
                        }
                        resultMap.put("list", listDataModel);
                        template.process(resultMap, out);

                        DataModel model = new FileDataModel(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "templates/dataModel.txt"));
                        UserSimilarity user = new EuclideanDistanceSimilarity(model);
                        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
                        Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
                        LongPrimitiveIterator iter = model.getUserIDs();

                        List<RecommendedItem> list1 = r.recommend(new Long(userId), RECOMMENDER_NUM);
                        if (!list1.isEmpty()) {//推荐的不为空的话
                            for (RecommendedItem ritem : list1) {
                                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                                usertInId.add(ritem.getItemID());
                            }
                        }
                    }
                }
                if (!usertInId.isEmpty()) {
                    QueryWrapper<News> objectSongWrapper2 = new QueryWrapper<>();
                    objectSongWrapper2.in("news_id", usertInId);
                    list31 = newsService.getBaseMapper().selectList(objectSongWrapper2);


                }


            }


            return ResponseData.success(list31);


        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }


    }

    /**
     * 确认预约完成
     *
     * @return
     */
    @PostMapping(value = "/updateOrder")
    public ResponseData updateOrder(Long id, Integer status) {

        try {

            Order order = orderService.getById(id);
            order.setStatus(status);
            orderService.updateById(order);
            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }


    }

    /**
     * 删除预约
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteOrder")
    public ResponseData deleteOrder(Long id) {

        try {

            orderService.removeById(id);
            return ResponseData.success();

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }


    @GetMapping(value = "/getHealthyService")
    public ResponseData getHealthyService(Long serviceId) {

        try {

            HealthyService healthyService = healthyServiceService.getById(serviceId);

            return ResponseData.success(healthyService);
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }

    /**
     * 用户预约
     *
     * @param orderParam
     * @return
     */
    @PostMapping(value = "/addOrder")
    public ResponseData addOrder(@RequestBody OrderParam orderParam) {

        try {


            orderParam.setOrderNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            orderParam.setStatus(0);
            orderParam.setTime(new Date());
            orderService.add(orderParam);

            return ResponseData.success();
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }


    @PostMapping(value = "/getRecord")
    public ResponseData getRecord(Long recordId) {

        try {

            Record record = recordService.getById(recordId);

            RecordResult recordResult = new RecordResult();
            ToolUtil.copyProperties(record, recordResult);
            User user = userService.getById(record.getOpeId());
            if (null != user) {
                recordResult.setOpeName(user.getName());
                recordResult.setTel(user.getPhone());
            }

            RegisteredUsers registeredUsers = registeredUsersService.getById(record.getUserId());
            if (null != registeredUsers) {
                recordResult.setName(registeredUsers.getName());
            }

            return ResponseData.success(recordResult);
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }


    /**
     * 用户一键呼救
     *
     * @param locationParam
     * @return
     */
    @PostMapping(value = "/rescue")
    public ResponseData rescue(@RequestBody LocationParam locationParam) {

        try {
            locationParam.setStatus(0);
            locationParam.setTime(new Date());
            locationService.add(locationParam);
            return ResponseData.success();
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }


    @PostMapping(value = "/getRecordList")
    public ResponseData getRecordList(Long userId) {

        try {

            QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            List<Record> list = recordService.list(queryWrapper);
            List<RecordResult> listData = new ArrayList<>();
            for (Record record : list) {
                RecordResult recordResult = new RecordResult();
                ToolUtil.copyProperties(record, recordResult);
                User user = userService.getById(record.getOpeId());
                if (null != user) {
                    recordResult.setOpeName(user.getName());
                    recordResult.setTel(user.getPhone());
                }

                RegisteredUsers registeredUsers = registeredUsersService.getById(record.getUserId());
                if (null != registeredUsers) {
                    recordResult.setName(registeredUsers.getName());
                }
                listData.add(recordResult);
            }


            return ResponseData.success(listData);
        } catch (Exception e) {

            return ResponseData.error(e.getMessage());
        }


    }


    /**
     * 修改用户信息
     *
     * @param registeredUsersParam
     * @return
     */
    @PostMapping(value = "/editUser")
    public ResponseData editUser(@RequestBody RegisteredUsersParam registeredUsersParam) {

        try {
            if (ToolUtil.isNotEmpty(registeredUsersParam.getPassWord())) {
                String string = MD5Util.encrypt(registeredUsersParam.getPassWord());
                registeredUsersParam.setPassWord(string);
            }

            registeredUsersService.update(registeredUsersParam);

            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }

    /**
     * 用户登录
     *
     * @param registerUserParam
     * @return
     */
    @PostMapping(value = "/loginUser")
    public ResponseData loginrUser(@RequestBody RegisteredUsersParam registerUserParam) {

        try {
            QueryWrapper<RegisteredUsers> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", registerUserParam.getUserName());
            RegisteredUsers registerUser = registeredUsersService.getOne(queryWrapper);
            if (null == registerUser) {
                return ResponseData.error("用户不存在");
            }
            String string = MD5Util.encrypt(registerUserParam.getPassWord());
            if (!registerUser.getPassWord().equals(string)) {
                return ResponseData.error("密码错误");
            }

            Login login = new Login();

            login.setNickName(registerUser.getName());
            login.setId(registerUser.getUserId());
            login.setHeadIcon(registerUser.getPic());
            login.setCode(registerUser.getType());
            return ResponseData.success(login);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }


    /**
     * 上传图片
     *
     * @param uploadfile
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public ResponseData imgUpload(@RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile) {
        List<String> pics = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            String fileSavePath = "";
            String serverSavePath = "";
            String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(file.getOriginalFilename());
            try {
                fileSavePath = gunsProperties.getFileUploadPath();
                serverSavePath = gunsProperties.getServerUploadPath();
                file.transferTo(new File(fileSavePath + pictureName));
            } catch (Exception e) {
                throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
            }
            pics.add(serverSavePath + pictureName);
        }

        return ResponseData.success(200, "上传成功", pics.get(0));
    }


    /**
     * 查询新闻详情
     *
     * @param newsId
     * @return
     */
    @PostMapping(value = "/getNewInfo")
    public ResponseData getNewInfo(Long newsId, Long userId) {

        try {
            News news = newsService.getById(newsId);
            news.setContent(news.getContent().replace("& lt;", "<")
                    .replace("& gt;", ">"));

            NewsResult result = new NewsResult();
            ToolUtil.copyProperties(news, result);
            if (null != userId) {
                QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", userId);
                queryWrapper.eq("news_id", newsId);
                Collect collect = collectService.getOne(queryWrapper);
                if (null != collect) {
                    result.setT(true);
                } else {
                    result.setT(false);
                }
            }


            return ResponseData.success(result);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }


    }

    /**
     * 资讯收藏
     *
     * @param collectParam
     * @return
     */
    @PostMapping(value = "/collect")
    public ResponseData collect(@RequestBody CollectParam collectParam) {

        try {
            QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", collectParam.getUserId());
            queryWrapper.eq("news_id", collectParam.getNewsId());
            Collect collect = collectService.getOne(queryWrapper);
            if (null != collect) {
                collectService.removeById(collect.getCollectId());
            } else {
                collectService.add(collectParam);
            }


            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }

    }


    /**
     * 用户账号信息查询
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/getinfo")
    public ResponseData getinfo(Long userId) {

        try {

            RegisteredUsers registeredUsers = registeredUsersService.getById(userId);

            return ResponseData.success(registeredUsers);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }


    }


}
