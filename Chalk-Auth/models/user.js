const mongoose = require("mongoose"),
      Schema = mongoose.Schema,
      passportLocalMongoose = require("passport-local-mongoose")

// Would like to guarantee the email to be unique https://www.makeuseof.com/nodejs-google-authentication/
var User = new Schema({
      username: {type:String,unique:true},
      role: String,
      name:String,
      active: Boolean,
      date_created: String,
      last_access: String
})
    
User.plugin(passportLocalMongoose)

module.exports = mongoose.model("User",User)