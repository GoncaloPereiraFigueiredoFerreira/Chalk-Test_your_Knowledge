var createError = require("http-errors");
var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");

var indexRouter = require("./routes/index");

var app = express();

var mongoose = require("mongoose");
var connectString = process.env.MONGODB;
mongoose.connect(connectString);
var db = mongoose.connection;

db.on("error", (err) => {
  console.log(err);
  process.exit();
});
db.on("open", () => {
  console.log("Connexão ao mongo realizada com sucesso...");
});

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

app.use("/", indexRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};
  console.error("Error: " + err);
  // send error status
  res.sendStatus(err.status || 500);
});

module.exports = app;
