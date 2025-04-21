const app = getApp();
var uploadFileServlet = app.globalData.local_url + "api/upload";

var save_url = app.globalData.local_url + "api/saveTemperature";

Page({
  data: {
    index: null,
    date: '2021-11-30',
    enddate: '2021-11-30',
    imgList: [],
    index: 0,
    picker: ["男","女"],
    name: "",
    suspicious: "",
    vaccines: "",
    state: "",
    suspicious: "",
    temperature: "",
    age: "",
    type:"",
    city:"",
  },
  onShow: function () {
  if(!app.globalData.isLogin) {
    wx.navigateTo({
      url: '/pages/about/login/login'
    })
  }
  var that = this;
  wx.getLocation({
    type: 'wgs84',
    success(res) {
      const latitude = res.latitude
      const longitude = res.longitude
      const speed = res.speed
      const accuracy = res.accuracy
      that.curCity(longitude, latitude)
    }
  })






  },
  curCity: function (longitude, latitude) {
    var _this = this;
    wx.request({
      url: 'http://api.map.baidu.com/reverse_geocoding/v3/?ak=phNSNoj4rT4iAQgGWoCTd5Vdmqb8dUm1&output=json&coordtype=wgs84ll&location=' + latitude + ',' + longitude,
      data: {},
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        var city = res.data.result.formatted_address
        console.log("定位的城市" + city)
       var location =  latitude + ',' + longitude;
       _this.setData({
        city: city
      });
        

      }
    })
  },
  getName: function (e) {
    var val = e.detail.value;
    this.setData({
      name: val
    });
  },   
  getAge: function (e) {
    var val = e.detail.value;
    this.setData({
      age: val
    });
  },  
  getAddress: function (e) {
    var val = e.detail.value;
    this.setData({
      city: val
    });
  }, 
  func:function(e){
    var val=e.detail.value;//获取radio值，类型：字符串
    console.log(val);
    this.setData({
      suspicious: val
    });
  },
  func1:function(e){
    var val=e.detail.value;//获取radio值，类型：字符串
    console.log(val);
    this.setData({
      vaccines: val
    });
  },
  func2:function(e){
    var val=e.detail.value;//获取radio值，类型：字符串
    console.log(val);
    this.setData({
      state: val
    });
  },
  getTemperature:function(e){
    var val=e.detail.value;//获取radio值，类型：字符串
    console.log(val);
    this.setData({
      temperature: val
    });
  },
  save(e){
    var that = this;
    var name  = that.data.name
    if (name == ""){
      wx.showModal({
        title: '提示',
        content: '请输入姓名',
        success: function (res) {
        }
      })
      return;
    }
    var age = that.data.age
    if (age == "") {
      wx.showModal({
        title: '提示',
        content: '请输入年龄',
        success: function (res) {

        }
      })

      return;
    }
    var city = that.data.city
    if (city == "") {
      wx.showModal({
        title: '提示',
        content: '请输入居住地址',
        success: function (res) {
        }
      })
      return;
    }
    var suspicious = that.data.suspicious
    if (suspicious == "") {
      wx.showModal({
        title: '提示',
        content: '请选择是否接触过可疑人员',
        success: function (res) {
        }
      })
      return;
    }

    var vaccines = that.data.vaccines
    if (vaccines == "") {
      wx.showModal({
        title: '提示',
        content: '请选择是否接种疫苗',
        success: function (res) {
        }
      })
      return;
    }
    var state = that.data.state
    if (state == "") {
      wx.showModal({
        title: '提示',
        content: '请选择身体状态',
        success: function (res) {
        }
      })
      return;
    }

    var temperature = that.data.temperature
    if (temperature == "") {
      wx.showModal({
        title: '提示',
        content: '请输入当前体温',
        success: function (res) {
        }
      })
      return;
    }
   var index = that.data.index;
    if (index == -1) {
      wx.showModal({
        title: '提示',
        content: '请选择性别',
        success: function (res) {
        }
      })
      return;
    }
    wx.request({
      url: save_url,
      data: {
        name: that.data.name,
        age: that.data.age,
        userId :app.globalData.userId,
        address: city,
        sex:that.data.picker[that.data.index],
        suspicious: suspicious,
        vaccines: vaccines,
        state: state,
        temperature:temperature
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

  PickerChange(e) {
    console.log(e);
    this.setData({
      index: e.detail.value
    })
  },
  DateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },
  DateChange1(e) {
    this.setData({
      enddate: e.detail.value
    })
  },
  ChooseImage() {
    wx.chooseImage({
      count: 6, //默认9
      sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], //从相册选择
      success: (res) => {

        var that = this;
        // if (this.data.imgList.length != 0) {
        //   this.setData({
        //     imgList: this.data.imgList.concat(res.tempFilePaths)
        //   })
        // } else {
        //   this.setData({
        //     imgList: res.tempFilePaths
        //   })
        // }
        for (let i in res.tempFilePaths) {
          console.log(uploadFileServlet)
          wx.uploadFile({
            url: uploadFileServlet,
            filePath: res.tempFilePaths[i],
            name: "uploadfile",
            success: function (res) {
             var pics =  JSON.parse(res.data)
              console.log(pics)
              that.setData({
                imgList: that.data.imgList.concat(pics.data[0])
             })
             
            },
            fail: function (err) {
              console.log("有图片上传失败")
            },
            complete: function () {


            }
          })


        }

        












      }
    });
  },
  ViewImage(e) {
    wx.previewImage({
      urls: this.data.imgList,
      current: e.currentTarget.dataset.url
    });
  },
  DelImg(e) {
    wx.showModal({
      title: '提示',
      content: '确定要删除这张照片吗？',
      cancelText: '再看看',
      confirmText: '再见',
      success: res => {
        if (res.confirm) {
          this.data.imgList.splice(e.currentTarget.dataset.index, 1);
          this.setData({
            imgList: this.data.imgList
          })
        }
      }
    })
  },
});

