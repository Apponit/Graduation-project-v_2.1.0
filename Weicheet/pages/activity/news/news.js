const app = getApp();
Component({
  options: {
    addGlobalClass: true,
  },
  data: {
    CustomBar: app.globalData.CustomBar,
    list:[],
    icon:"",
    Tab:[],
    TabCur: 0,
    types:"",
    index: 0,
    id: null,
    windowHeight: 0,//获取屏幕高度
    typeList:[],
    swiperList: [ {
      id: 2,
      type: 'image',
        url: 'https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2020%2F1112%2F4ef80281j00qjoi67001ud200u000gwg00it00al.jpg&thumbnail=660x2147483647&quality=80&type=jpg'
    },{
      id: 0,
      type: 'image',
      url: 'https://img1.baidu.com/it/u=4038406728,511560852&fm=253&fmt=auto&app=120&f=JPEG?w=1080&h=608'
    }, {
      id: 1,
        type: 'image',
        url: 'https://img0.baidu.com/it/u=3733979832,2098457770&fm=253&fmt=auto&app=138&f=JPEG?w=409&h=240',
    }],
  },
  //自定义组件
  attached: function () {

    var that = this;
    wx.getSystemInfo({
        success: function (res) {
            that.setData({
                windowHeight: res.windowHeight
            })
        }
    })
    getTypeData(that,"")
  }, methods: {
    tabSelect(e) {
      this.setData({
        TabCur: e.currentTarget.dataset.id,
        types: this.data.typeList[e.currentTarget.dataset.id].name,
        scrollLeft: (e.currentTarget.dataset.id-1)*60
      })
   if(e.currentTarget.dataset.id == 0 ){
      getData(this,"")
   }else{
    getData(this,"")
   }
 },
    getData(e) {

      var that = this;

      that.setData({
        icon: app.globalData.local_url,
      })

      var val = e.detail.value;
     
    
      getData(that, val);
    }, toReply(e) {
      var id = e.currentTarget.dataset.id;
      app.globalData.id = id;
      wx.navigateTo({
        url: '/pages/basics/detailed/detailed?id=' + id
      })

    }  ,rescue(e) {
      if (!app.globalData.isLogin) {
        wx.navigateTo({
          url: '/pages/about/login/login'
        })
      }else{
      wx.showModal({
        title: '提示',
        content: '确定呼救吗？',
        success: function (res) {
          console.log(res)
          if (res.confirm) {
          wx.getLocation({
            type: 'wgs84', 
            success(res) {
              console.log(res)
              const lat = res.latitude
              const lng = res.longitude
              var get_url = app.globalData.local_url + "api/rescue";
              wx.request({
                url: get_url,
                data: {
                  lat:lat,
                  lng:lng,
                  userId:app.globalData.userId
                },
                method: 'POST',
                header: {
                  'content-type': 'application/json'
                },
                success: function (res) {
                  if (res.data.code == 200) {
                    wx.showToast({
                      title: '呼救成功',
                      icon: 'success',
                      duration: 2000 //持续的时间
                    });
                    console.log("11111111111111")
                   
                  } else {
                  
                  }
                }
            })
            }, fail: function (errInfo) {
              console.info(errInfo)
            }
          })
        }
        }
      })
  
    }
    },


  }




})

function getTypeData(that) {
  var get_url = app.globalData.local_url + "api/getTypeList";

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
          typeList:res.data.data
        });

        for (let i in res.data.data) {
          that.setData({
            Tab: that.data.Tab.concat(res.data.data[i].name),
            types:res.data.data[0].name
            })
        }

        getData(that,"");
      } else {

      }
    }
  })
}
function getData(that,val){

  var get_url = app.globalData.local_url + "api/getNewList?name="+val+"&userId="+app.globalData.userId+"&type="+that.data.types ;
  
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

      } else {

      }
    }
  })


}