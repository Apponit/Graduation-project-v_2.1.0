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
    
  var get_url = app.globalData.local_url + "api/getForum?forumId=" + app.globalData.id;
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
          item: res.data.data
        });

      } else {

      }
    }
  })


  }, methods: {
    content: function (e) {
      this.setData({
        content: e.detail.value
      })
    },
    ViewImage(e) {
      console.log(e.currentTarget.dataset.index);
      wx.previewImage({
        urls: this.data.item.pics,
        current: e.currentTarget.dataset.url
      });
    },
    give(e) {
      var that = this;
      var id = e.currentTarget.dataset.id;
      if (!app.globalData.isLogin) {
        wx.navigateTo({
          url: '/pages/about/login/login'
        })
      }else{


      var get_url = app.globalData.local_url + "api/give?forumId=" +  app.globalData.id;
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
        getData(that,that.data.name);

      } 
    }
  }) 
      }
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
            getData(that);
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