const app = getApp();
Component({
  options: {
    addGlobalClass: true,
  },
  data: {
    CustomBar: app.globalData.CustomBar,
    list: [],
    item:{},
    content:""
  },
  //自定义组件
  attached: function (e) {
    var that = this;
    getData(that)
    wx.request({
      url: app.globalData.local_url + 'api/getActivityInfo?activityId='+app.globalData.id+"&userId="+app.globalData.userId,
      data: {
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

  }, methods: {
    content: function (e) {
      this.setData({
        content: e.detail.value
      })
    },
    shouchang(e){
      var that = this;  
      var status =  e.currentTarget.dataset.status
      var me = "确认报名吗？";
      if(status == 1){
        me = "确认取消报名吗？";
      }
      wx.showModal({
        title: '提示',
        content: me,
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
              url: app.globalData.local_url + 'api/enlist',
              data: {
                userId: userId,
                activityId: id,
                type: 1
              },
              header: {
                'content-type': 'application/json'
              },
              method: 'POST',
              success: function (res) {
                if (res.data.code == 200) {
                  wx.showToast({
                    title: '操作成功',
                    icon: 'success',
                    duration: 2000 //持续的时间
                  });

                  wx.request({
                    url: app.globalData.local_url + 'api/getActivityInfo?activityId='+app.globalData.id+"&userId="+app.globalData.userId,
                    data: {
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
    },
    send() {
      if (!app.globalData.isLogin) {
        wx.navigateTo({
          url: '/pages/about/login/login'
        })
      }
      var that = this;
     
      var content = this.data.content;
      wx.request({
        url: app.globalData.local_url + 'api/saveComment',
        data: {
          userId: app.globalData.userId,
          content: content,
          forumId: app.globalData.id
        },
        method: 'POST',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          if (res.data.code == 200) {
            that.setData({
              content:""
            })
            wx.showToast({
              title: '评论成功',
              icon: 'success',
              duration: 2000 //持续的时间
            });

            getData(that) 
          } else {

          }
        }
      })
    }

  }




})


function getData(that) {

  var get_url = app.globalData.local_url + "api/getCommentList?forumId=" + app.globalData.id;

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