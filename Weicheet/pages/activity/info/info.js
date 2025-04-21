const app = getApp();
Page({
  data: {
    TabCur: 0,
    scrollLeft:0,
    info:null,
    userPass: "",
    id:null,
    startdate:"2025-01-01",
    list:[],
    content:""
  },
  userPass: function (e) {
    this.setData({
      userPass: e.detail.value
    })
  },
  getcontent: function (e) {
    this.setData({
      content: e.detail.value
    })
  },
  DateChange(e) {
    this.setData({
      startdate: e.detail.value
    })
  },
  toReply(e){
    if(!app.globalData.isLogin) {
      wx.navigateTo({
        url: '/pages/about/login/login'
      })
    }else{
      var id = e.currentTarget.dataset.id;
      wx.makePhoneCall({
        phoneNumber: id, //此号码并非真实电话号码，仅用于测试
        success:function(){
          console.log("拨打电话成功！")
        },
        fail:function(){
          console.log("拨打电话失败！")
        }
      })
      
    }
    
  },
  onLoad: function (options) {
    this.setData({
      id:options.id
    })
    this.init();
    getData(this,options.id);
  },
  init(){
    var id = this.data.id;
    var that = this;
    var userId = app.globalData.userId;
    wx.request({
      url: app.globalData.local_url + 'api/getHealthyService',
      data: {
        serviceId : id,
        userId: userId
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data.data);
          that.setData({
            info: res.data.data
          });   
        
      }
    })
  },
  showModal(e) {
    var that = this;
    if (!app.globalData.isLogin) {
      wx.navigateTo({
        url: '/pages/about/login/login'
      })
      return;
    } 
    this.setData({
      modalName: e.currentTarget.dataset.target
     
    })
  }, hideModal(e) {
   
    this.setData({
      modalName: null
    })
  },  pay(e) {
   
    var that = this;
    if (that.data.userPass !='123456'){
      wx.showToast({
        title: "支付密码不正确",
        icon: 'none',
        duration: 2000 //持续的时间
      });
      return;
    }
    if (that.data.startdate =="2025-01-01"){
      wx.showToast({
        title: "请选择时间",
        icon: 'none',
        duration: 2000 //持续的时间
      });
      return;
    }
    var userId = app.globalData.userId;
    wx.request({
      url: app.globalData.local_url + 'api/addOrder',
      data: {
        serviceId: that.data.info.serviceId,
        userId: userId,
        message:that.data.content,
        time:that.data.startdate
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.code == 200) {
          wx.showToast({
            title: '预约成功',
            icon: 'success',
            duration: 2000 //持续的时间
          });
          that.setData({
            modalName: null
          })
         
        } else {

        }
      }
    })
},
  addcart(e){
    if (!app.globalData.isLogin) {
      wx.navigateTo({
        url: '/pages/about/login/login'
      })
      return;
    }
    var id = e.currentTarget.dataset.id;
    var userId = app.globalData.userId;
    wx.request({
      url: app.globalData.local_url + 'api/addShopCar', 
      data:{
        userId: userId ,
        mallId: id,
      },
      header: {
        'content-type': 'application/json'
      },
      method: 'POST',
      success: function(res) {
        if(res.data.code == 200){
          wx.showToast({
            title: '加入购物车成功',
            icon: 'success',
            duration: 2000 //持续的时间
          });
        }else if(res.data.code == 500){
          wx.showToast({
            title: '已经加入购物车不要重复添加！',
            icon: 'success',
            duration: 2000 //持续的时间
          });
        }else{
          wx.showToast({
            title: '操作失敗',
            icon: 'none',
            duration: 2000 //持续的时间
          });
        }
      }
    })
  },
  buying(e){
    if (!app.globalData.isLogin) {
      wx.navigateTo({
        url: '/pages/about/login/login'
      })
      return;
    }
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/activity/publish/publish?id='+id
    })
},
  order(e){
    wx.showModal({
      title: '提示',
      content: '确认预约吗？',
      success: function (res) {
        if (res.confirm) {
          if (!app.globalData.isLogin) {
            wx.navigateTo({
              url: '/pages/about/login/login'
            })
            return;
          }
          var id = e.currentTarget.dataset.id;
          var userId = app.globalData.userId;
          wx.request({
            url: app.globalData.local_url + 'api/addAppointment',
            data: {
              userId: userId,
              mallId: id,
              type:0
            },
            header: {
              'content-type': 'application/json'
            },
            method: 'POST',
            success: function (res) {
              if (res.data.code == 200) {
                wx.showToast({
                  title: '预约成功',
                  icon: 'success',
                  duration: 2000 //持续的时间
                });
              } else {
                wx.showToast({
                  title: res.data.message,
                  icon: 'success',
                  duration: 2000 //持续的时间
                });
              }
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  }, shouchang(e){
    wx.showModal({
      title: '提示',
      content: '确认收藏吗？',
      success: function (res) {
        if (res.confirm) {
          if (!app.globalData.isLogin) {
            wx.navigateTo({
              url: '/pages/about/login/login'
            })
            return;
          }
          var id = e.currentTarget.dataset.id;
          var userId = app.globalData.userId;
          wx.request({
            url: app.globalData.local_url + 'api/addAppointment',
            data: {
              userId: userId,
              mallId: id,
              type: 1
            },
            header: {
              'content-type': 'application/json'
            },
            method: 'POST',
            success: function (res) {
              if (res.data.code == 200) {
                wx.showToast({
                  title: '收藏成功',
                  icon: 'success',
                  duration: 2000 //持续的时间
                });
              }else{
                wx.showToast({
                  title: res.data.message,
                  icon: 'success',
                  duration: 2000 //持续的时间
                });
              }
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  }
})




function getData(that,id) {

  var get_url = app.globalData.local_url + "api/getCommentList?forumId=" + id;

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
          list: res.data.data
        });

      } else {

      }
    }
  })

}