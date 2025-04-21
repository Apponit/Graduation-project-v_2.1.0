const app = getApp();
Page({
  data: {
    TabCur: 0,
    scrollLeft:0,
    list:null,
    type :null,
    title:"搜索"
  },
  onLoad(option){
    var that = this;
    that.setData({
      type: option.type,
      title:option.type
    });
    var userId = app.globalData.userId;
      var get_url = app.globalData.local_url + "api/getNewList?type="+option.type ;
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
    
    
   
  },
  search(e) {
    var that=this;
    var val = e.detail.value;
    var get_url = app.globalData.local_url + "api/getNewList?name="+val+"&type="+that.data.type ;
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
  },
  goInfo(e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
    url: '/pages/basics/detailed/detailed?id=' + id
    })
  }
  

})