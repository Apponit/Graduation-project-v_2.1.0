const app = getApp();
Component({
  options: {
    addGlobalClass: true,
  },
  data: {
    CustomBar: app.globalData.CustomBar,
    allList:null,
    news:null
  }, 
  attached(){
    var that = this;  
    wx.request({
      url: app.globalData.local_url + 'api/getActivityList',
      data: {
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.code == 200) {
          that.setData({
            allList: res.data.data,
          });   

        } else {
          
        }
      }
    })

  },
  methods: {
    getData(e) {
      var that = this;
      var val = e.detail.value;
      var that = this;  
      wx.request({
        url: app.globalData.local_url + 'api/getActivityList?name='+val,
        data: {
        },
        method: 'GET',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          if (res.data.code == 200) {
            that.setData({
              allList: res.data.data,
            });   
  
          } else {
            
          }
        }
      })
    },
 
    goInfo(e) {
      console.log("111");
      var id = e.currentTarget.dataset.id;
      app.globalData.id = id;
      console.log(id);
      wx.navigateTo({
      url: '/pages/activity/reply/reply?id=' + id
      })
    }
 },
})
