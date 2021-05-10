//引入nodemailer
const nodemailer = require('nodemailer');
let transporter = nodemailer.createTransport({
  service:'qq',
  port:465,
  secure:true,
  auth:{
    user:'380353873@qq.com',
    pass:'lwldfarfhgrebjai'
  }
});

function sendMail(mail,type,code,call){
  var content;
  if(type=='signup'){
    content = 'KeepUp:\nWelcome to sign up our application, your authentification code is: ';
  }else if(type=='forgetpwd'){
    content = 'KeepUp:\nDear customer, you can change your password with your authentification code: ';
  }
  let mailOptions = {
    from: '"Keep-Up"<380353873@qq.com>',
    to: mail,
    subject: 'Keep-Up sign-up',
    text: content + code,
    //html: '<p>这里是"Express-demo"详情请点击:</p><a href="https://www.jianshu.com/u/5cdc0352bf01">点击跳转</a>', //页面内容
    // attachments: [{//发送文件
    //      filename: 'index.html', //文件名字
    //      path: './index.html' //文件路径
    //  },
    //  {
    //      filename: 'sendEmail.js', //文件名字
    //      content: 'sendEmail.js' //文件路径
    //  }
    // ]
  }

  //发送函数
  transporter.sendMail(mailOptions,(err,info)=>{
    if(err){
      call(false)
    }else{
      call(true)
    }
  });
}

module.exports = {
  sendMail
}
