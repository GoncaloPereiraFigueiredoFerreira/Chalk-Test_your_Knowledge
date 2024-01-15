var express = require("express");
var router = express.Router();
var passport = require("passport");
var axios = require("axios");
// User model
var User = require("../models/user");
require("dotenv").config();

// Parse Json
const bodyParser = require("body-parser");
router.use(bodyParser.json());

// Get our authenticate module
var authenticate = require("../auth_strat");

router.post("/register", (req, res, next) => {
  var data = new Date().toISOString().substring(0, 16);
  // Create User
  User.register(
    new User({
      username: req.body.email,
      name: req.body.name,
      role: req.body.role,
      active: true,
      date_created: data,
      last_access: data,
    }),
    req.body.password,
    (err, user) => {
      if (err) {
        res
          .status(403)
          .setHeader("Content-Type", "application/json")
          .jsonp({
            sucess: false,
            err: err,
          })
          .end();
      } else {
        var token = authenticate.getToken({
          username: req.body.email,
          role: req.body.role,
          name: req.body.name,
        });
        res
          .status(200)
          .setHeader("Content-Type", "application/json")
          .setHeader("Authorization", "Bearer " + token)
          .cookie("chalkauthtoken", token)
          .jsonp({
            sucess: true,
            user: {
              username: req.body.email,
              role: req.body.role,
              name: req.body.name,
            },
          })
          .end();
      }
    }
  );
});

router.post(
  "/login",
  passport.authenticate("local", { session: false }),
  (req, res) => {
    if (req.user.active) {
      // Create a token
      var token = authenticate.getToken({
        username: req.user.username,
        role: req.user.role,
        name: req.user.name,
      });
      var date = new Date().toISOString().substring(0, 16);
      User.updateOne(
        { username: req.user.username },
        { last_access: date }
      ).then(() => {
        res
          .status(200)
          .setHeader("Content-Type", "application/json")
          .setHeader("Authorization", "Bearer " + token)
          .cookie("chalkauthtoken", token)
          .jsonp({
            success: true,
            user: {
              username: req.user.username,
              role: req.user.role,
              name: req.user.name,
            },
          })
          .end();
      });
    } else {
      res
        .status(401)
        .setHeader("Content-Type", "application/json")
        .json({ success: false, status: "Deactivated Account" })
        .end();
    }
  }
);

router.post("/google", (req, res, next) => {
  var data = new Date().toISOString().substring(0, 16);
  axios
    .get("https://www.googleapis.com/oauth2/v3/userinfo?alt=json", {
      headers: { Authorization: `Bearer ${req.body.acess_token}` },
    })
    .then((gres2) => {
      User.findOne({ username: gres2.data.email }).then((result) => {
        let role = "";
        if (result == null) {
          role = req.body.role;
          User.create(
            new User({
              username: gres2.data.email,
              name: gres2.data.name,
              role: req.body.role,
              active: true,
              date_created: data,
              last_access: data,
            })
          );
        } else {
          role = result.role;
          User.updateOne({ username: gres2.data.email }, { last_access: data });
        }

        var token = authenticate.getToken({
          username: gres2.data.email,
          role: role,
          name: gres2.data.name,
        });
        res
          .status(200)
          .setHeader("Content-Type", "application/json")
          .cookie("chalkauthtoken", token)
          .jsonp({
            sucess: true,
            user: {
              username: gres2.data.email,
              role: role,
              name: gres2.data.name,
            },
          })
          .end();
      });
    });
});

router.get("/user", authenticate.verifyUser, (req, res, next) => {
  res
    .status(200)
    .jsonp({
      email: req.user.username,
      role: req.user.role,
      name: req.user.name,
    })
    .end();
});

module.exports = router;
