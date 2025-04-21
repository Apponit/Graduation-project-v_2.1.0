const app = getApp();
Component({
  options: {
    addGlobalClass: true,
  },
 
  data: {
    CustomBar: app.globalData.CustomBar,
    list: [],
    index: null,
    id: null,
    picker: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
    type:false,
    content:null
  },
  //自定义组件
  attached: function () {
    var that = this;
   
    if (!app.globalData.isLogin) {
      wx.navigateTo({
        url: '/pages/about/login/login'
      })
    }else{
      getData(that, "")
    }

  },moved: function () {
    

  }, methods: {
    getData(e) {
      var val = e.detail.value;
      var that = this;
      getData(that, val)


    }, toReply(e){
      var id = e.currentTarget.dataset.id;
      app.globalData.id = id;
      wx.navigateTo({
        url: '/pages/basics/chat/chat?id=' + id
      })
      
    }
    , showModal(e) {
      var id = e.currentTarget.dataset.id;
      
      this.setData({
        type: !this.data.type,
        id: id
      });

    },
    hideModal(e) {
      this.setData({
        modalName: null
      })
    },
    content: function (e) {
      this.setData({
        content: e.detail.value
      })
    },
    send(e) {
      if (null == this.data.content || "" == this.data.content) {
       
       return ;
      }

      this.setData({
        modalName: e.currentTarget.dataset.target
      })
      
    },PickerChange(e) {
      console.log(e);
      var that = this;
      this.setData({
        modalName: null
      })
      this.setData({
        index: e.detail.value
      })

      var that = this;
      var content = this.data.content;
      wx.request({
        url: app.globalData.local_url + 'api/saveScore',
        data: {
          applyId: that.data.id,
          adminMark: that.data.content,
          score: that.data.picker[that.data.index]
        },
        method: 'POST',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          if (res.data.code == 200) {
            that.setData({
              content: "",
              type:false
            })
            getData(that);
          } else {

          }
        }
      })


    }
  }




})

function getData(that, val) {
  var get_url = app.globalData.local_url + "api/getMessageList?&userId=" + app.globalData.userId;
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