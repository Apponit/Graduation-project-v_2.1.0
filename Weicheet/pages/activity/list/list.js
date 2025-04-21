const app = getApp();
Component({
  options: {
    addGlobalClass: true,
  },
  data: {
    CustomBar: app.globalData.CustomBar,
    list:[],
     
  },
  //自定义组件
  attached: function () {
    var that = this;
     
    var get_url = app.globalData.local_url + "api/getRecordList?userId="+app.globalData.userId ;
  
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
    

  } ,methods: {
     delete(e){
      var that = this;
       var id = e.currentTarget.dataset.id;
       var delete_url = app.globalData.local_url + "api/deleteNucleicAcidh?id="+id ;
       wx.showModal({
        title: '提示',
        content: '确认删除吗？',
        success: function (res) {
          if (res.confirm) {
          wx.request({
            url: delete_url,
            data: {
        
            },
            method: 'POST',
            header: {
              'content-type': 'application/json'
            },
            success: function (res) {
              if (res.data.code == 200) {
              
    var get_url = app.globalData.local_url + "api/getNucleicAcidhList?userId="+app.globalData.userId ;
  
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
        
              } else {
        
              }
            }
          })
        }
        } 
      })
    },
    see(e){
      console.log("111");
      var id = e.currentTarget.dataset.id;
      console.log(id);
      wx.navigateTo({
      url: '/pages/activity/report/report?id=' + id
      })
    }
   
  }




})



















