var express = require('express');
var router = express.Router();
var passport = require('passport');
 
// User model
var User = require('../models/user');

// Parse Json
const bodyParser = require('body-parser');
router.use(bodyParser.json());

// Get our authenticate module
var authenticate = require('../auth_strat');




router.post('/register', (req, res, next) => {
  var data = new Date().toISOString().substring(0,16)
  // Create User
  User.register(new User({
    username: req.body.email, 
    name:req.body.name,
    role:"user",
    active:true,
    date_created:data,
    last_access:data
  }),
    req.body.password, (err, user) => {
    if(err) {
      console.log(err)
      res.statusCode = 500;
      res.setHeader('Content-Type', 'application/json');
      res.json({success: false,err: err});
    }
    else {
      var token = authenticate.getToken({username: req.body.email, role: "user", name:req.body.name});
      res.statusCode = 200;
      res.setHeader('Content-Type', 'application/json');
      // Manda cookie e nao json
      res.json({success: true, token: token});
    }
  });
});

router.post('/login', passport.authenticate('local',{ session: false }), (req, res) => {
  if (req.user.active){
    // Create a token
    var token = authenticate.getToken({username: req.user.username, role: "user", name:req.user.name});
    // Response
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    var date = new Date().toISOString().substring(0,16)
    User.updateOne({username: req.user.username},{last_access:date}).then(()=>{
      res.json({success: true, token: token, status: 'You are successfully logged in!'});
    })
    
  }
  else
    res.json({success: false, status: 'Deactivated Account'});
});




module.exports = router;
