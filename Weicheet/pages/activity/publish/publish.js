const app = getApp();
var uploadFileServlet = app.globalData.local_url + "api/upload";

var save_url = app.globalData.local_url + "api/saveForum";

Page({
  data: {
    index: null,
    date: '2020-03-20',
    enddate: '2020-03-21',
    imgList: [],
    content: "",
    tel: "",
    list:[],
    index:0,
    picker: [],
    plateId:"",
    title: "",
    type:""
  },
  onShow: function () {
  if(!app.globalData.isLogin) {
    wx.navigateTo({
      url: '/pages/about/login/login'
    })
  }
  var get_url = app.globalData.local_url + "api/getPlateList" ;
  var that =this;
    wx.request({
      url: get_url,
      data: {
  
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.code == 200) {
          console.log(res.data.data);
          that.setData({
            list: res.data.data,
          });
          that.setData({
            picker: []
           })
          if (that.data.list.length != 0) {
            for (let i in that.data.list) {
              console.log(that.data.list[i].name);
              that.setData({
                picker: that.data.picker.concat(that.data.list[i].name),
                index:0,
               })
            }
              
          }
        } else {
  
        }
      }
    })


  },
  PickerChange(e) {
    console.log(e);
    this.setData({
      index: e.detail.value,
      plateId:this.data.list[e.detail.value].plateId
    })
    var that = this;
   
  },
  getcontent: function (e) {
    var val = e.detail.value;
    this.setData({
      content: val
    });
  },   
  gettel: function (e) {
    var val = e.detail.value;
    this.setData({
      tel: val
    });
  },  
  getaddress: function (e) {
    var val = e.detail.value;
    this.setData({
      title: val
    });
  }, 
  save(e){
    var that = this;
    var content  = that.data.content
    if (content == ""){
      wx.showModal({
        title: '提示',
        content: '请输入内容',
        success: function (res) {

        }
      })

      return;
    }
    var title = that.data.title
    if (title == "") {
      wx.showModal({
        title: '提示',
        content: '请输入标题',
        success: function (res) {

        }
      })

      return;
    }

    if (that.data.imgList.length == 0) {
      wx.showModal({
        title: '提示',
        content: '请上传图片',
        success: function (res) {
        }
      })
      return ;
    }

    var pic = "";
    for (let i in that.data.imgList) {
      pic += that.data.imgList[i]+",";
    }
    wx.request({
      url: save_url,
      data: {
        des: that.data.content,
        title: that.data.title,
        userId :app.globalData.userId,
        pic: pic,
        plateId:that.data.list[that.data.index].plateId
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {

        if (res.data.code == 200) {
          wx.showToast({
            title: "提交成功",
            icon: "success",
            duration: 10000
          });
         
          var timer = setTimeout(function () {
            clearTimeout(timer);
            wx.navigateBack()
          }, 1000);

        } else {
          wx.showModal({
            title: '提示',
            content: res.data.message,
            success: function (res) {

            }
          })
        }
      }
    })



  },

  PickerChange(e) {
    console.log(e);
    this.setData({
      index: e.detail.value
    })
  },
  DateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },
  DateChange1(e) {
    this.setData({
      enddate: e.detail.value
    })
  },
  ChooseImage() {
    wx.chooseImage({
      count: 6, //默认9
      sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], //从相册选择
      success: (res) => {

        var that = this;
        // if (this.data.imgList.length != 0) {
        //   this.setData({
        //     imgList: this.data.imgList.concat(res.tempFilePaths)
        //   })
        // } else {
        //   this.setData({
        //     imgList: res.tempFilePaths
        //   })
        // }
        for (let i in res.tempFilePaths) {
          console.log(uploadFileServlet)
          wx.uploadFile({
            url: uploadFileServlet,
            filePath: res.tempFilePaths[i],
            name: "uploadfile",
            success: function (res) {
             var pics =  JSON.parse(res.data)
              console.log(pics)
              that.setData({
                imgList: that.data.imgList.concat(pics.data)
             })
             
            },
            fail: function (err) {
              console.log("有图片上传失败")
            },
            complete: function () {


            }
          })


        }

        












      }
    });
  },
  ViewImage(e) {
    wx.previewImage({
      urls: this.data.imgList,
      current: e.currentTarget.dataset.url
    });
  },
  DelImg(e) {
    wx.showModal({
      title: '提示',
      content: '确定要删除这张照片吗？',
      cancelText: '再看看',
      confirmText: '再见',
      success: res => {
        if (res.confirm) {
          this.data.imgList.splice(e.currentTarget.dataset.index, 1);
          this.setData({
            imgList: this.data.imgList
          })
        }
      }
    })
  },
});
