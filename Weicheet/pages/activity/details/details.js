const app = getApp()
var list_url = app.globalData.apiUrl;

var page = 0;
Page({
  data: {
    id: 0,
    info: {}
  },
  shouchang: function (options) {
    var that = this;
    wx.request({
      url: app.globalData.local_url + "api/collect?newsId=" + that.data.id ,
      data: {
        newsId:that.data.id,
        userId:app.globalData.userId
      },
      header: {
        'content-type': 'application/json'
      },
      method: 'POST',
      dataType: 'json',
      responseType: 'text',
      success: function (res) {
        getloadData(that);
  
      },
      fail: function (res) {
  
      },
      complete: function (res) {
  
      },
    })
  },
  onLoad: function (options) {
    var that = this;

    this.setData({
      id: options.id
    })
    getloadData(that);
  }

  , answer(e){
    var that = this;
    if (!app.globalData.isLogin) {
      wx.navigateTo({
        url: '/pages/about/login/login'
      })
    } else {
      if(that.data.info.t){
        wx.navigateTo({
          url: '/pages/basics/exercise/exercise?id=' + that.data.id
        })
      }else{
        wx.navigateTo({
          url: '/pages/basics/answer/answer?id=' + that.data.id
        })
      }
     
    }



   
  }
})


function getloadData(that) {
  console.log("aaaaaaaa" + that.data.id);
  wx.showToast({
    title: '加载中',
    icon: "loading",
    duration: 10000
  });
  wx.request({
    url: app.globalData.local_url + "api/getNoticeInfo?id=" + that.data.id,
    data: {

    },
    header: {
      'content-type': 'application/json'
    },
    method: 'POST',
    dataType: 'json',
    responseType: 'text',
    success: function (res) {
      console.log(res.data);
      wx.hideToast();
      res.data.data.content = formatImg(res.data.data.content);
      res.data.data.content = res.data.data.content.replace(/\<img/gi, '<img class="rich-img" ');
      that.setData({
        info: res.data.data,
      })

      // 隐藏加载框
      wx.hideLoading();

    },
    fail: function (res) {

    },
    complete: function (res) {

    },
  })


}

function formatImg(html) {
  var newContent = html.replace(/<img[^>]*>/gi, function (match, capture) {
    var match = match.replace(/style=\"(.*)\"/gi, '<img class="rich-img" ');
    return match;
  });
  return newContent;
}