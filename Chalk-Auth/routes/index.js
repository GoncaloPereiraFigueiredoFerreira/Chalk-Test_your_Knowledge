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

const frontendAdress = process.env.FRONTEND;
const localPort = process.env.PORT;

// Get our authenticate module
var authenticate = require("../auth_strat");

router.post("/register", (req, res, next) => {
  var data = new Date().toISOString().substring(0, 16);
  // Create User
  User.register(
    new User({
      username: req.body.email,
      name: req.body.name,
      role: "user",
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
          role: "user",
          name: req.body.name,
        });
        res
          .status(200)
          .setHeader("Content-Type", "application/json")
          .cookie("chalkauthtoken", token)
          .jsonp({
            sucess: true,
            user: {
              username: req.body.email,
              role: "user",
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
        role: "user",
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
          .cookie("chalkauthtoken", token)
          .jsonp({
            success: true,
            user: {
              username: req.user.username,
              role: "user",
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

router.get("/google", (req, res, next) => {
  var data = new Date().toISOString().substring(0, 16);
  console.log(req.query.code);
  axios
    .post("https://oauth2.googleapis.com/token", {
      client_id: process.env.G_CLIENT_ID,
      client_secret: process.env.G_CLIENT_SECRET,
      code: req.query.code,
      grant_type: "authorization_code",
      redirect_uri: "http://localhost:" + localPort + "/google",
    })
    .then((gres) => {
      let token = gres.data.access_token;
      axios
        .get("https://www.googleapis.com/oauth2/v3/userinfo?alt=json", {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((gres2) => {
          if (!User.exists({ username: gres2.data.email })) {
            User.create(
              new User({
                username: gres2.data.email,
                name: gres2.data.name,
                role: "user",
                active: true,
                date_created: data,
                last_access: data,
              })
            );
          } else {
            User.updateOne(
              { username: req.user.username },
              { last_access: date }
            );
          }
          var token = authenticate.getToken({
            username: gres2.data.email,
            role: "user",
            name: gres2.data.name,
          });
          res
            .status(200)
            .setHeader("Content-Type", "application/json")
            .cookie("chalkauthtoken", token)
            .jsonp({
              sucess: true,
              user: {
                username: req.body.email,
                role: "user",
                name: req.body.name,
              },
            })
            .end();
        });
    });
});

module.exports = router;
