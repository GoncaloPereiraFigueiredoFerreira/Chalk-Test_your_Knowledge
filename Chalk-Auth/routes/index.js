var express = require("express");
var router = express.Router();
var passport = require("passport");
var axios = require("axios");
// User model
var User = require("../models/user");

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
      role: "user",
      active: true,
      date_created: data,
      last_access: data,
    }),
    req.body.password,
    (err, user) => {
      if (err) {
        console.log(err);
        res.statusCode = 500;
        res.setHeader("Content-Type", "application/json");
        res.json({ success: false, err: err });
      } else {
        var token = authenticate.getToken({
          username: req.body.email,
          role: "user",
          name: req.body.name,
        });
        res.cookie("chalkauthtoken", token);
        res.status(200);
        res.redirect("http://localhost:5173/");
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
      // Response
      res.statusCode = 200;
      res.setHeader("Content-Type", "application/json");
      var date = new Date().toISOString().substring(0, 16);
      User.updateOne(
        { username: req.user.username },
        { last_access: date }
      ).then(() => {
        res.cookie("chalkauthtoken", token);
        res.status(200);
        res.redirect("http://localhost:5173/");
      });
    } else res.json({ success: false, status: "Deactivated Account" });
  }
);

router.get("/google", (req, res, next) => {
  var data = new Date().toISOString().substring(0, 16);
  console.log(req.query.code);
  axios
    .post("https://oauth2.googleapis.com/token", {
      client_id:
        "665062865084-9ta4mjgrv95f7h5vi3cn7tbu9jmjah01.apps.googleusercontent.com",
      client_secret: "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",
      code: req.query.code,
      grant_type: "authorization_code",
      redirect_uri: "http://localhost:3000/google",
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
          res.cookie("chalkauthtoken", token);
          res.status(200);
          res.redirect("http://localhost:5173/");
        });
    });
});

module.exports = router;
