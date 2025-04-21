const app = getApp();
var save_url = app.globalData.local_url + "api/saveMeassage";
Page({
  data: {
    content:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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
    wx.request({
      url: save_url,
      data: {
        context: that.data.content,
        userId :app.globalData.userId,
        pid: 0,
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
  getcontent: function (e) {
    var val = e.detail.value;
    this.setData({
      content: val
    });
  }, 
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})