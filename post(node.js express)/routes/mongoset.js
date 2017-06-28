exports.mongoset = function mongoset(text, type) {

  try {
    // MongoDB へ 接続
    var MongoClient = require("mongodb").MongoClient;
    var url = "mongodb://localhost:27017/text"
    var moment = require("moment");
    console.log("コネクターインポート!");
    //現在日本時間取得;
    var jikan = moment().format('YYYY-MM-DD HH:mm:ss');
    console.log(jikan);
  } catch (err) {
    console.log("mongo接続JSインポート失敗");
  }
  MongoClient.connect(url, (error, db) => {
    // コレクションの取得
    var collection;
    collection = db.collection("text");
    //insert を行うメソッド
    collection.insertOne({
      'text': text,
      'type': type,
      "inserttime": jikan
    }, (error, result) => {
      db.close();
      console.log("insert end");
    });
  });
}
// exports.mongoget = function() {
//   // ドキュメント取得　MONGOSQL文↓
//   connecter("text", "text").find().toArray((error, documents) => {
//     //繰り返しdocumentsをdocumentに代入
//     for (var document of documents) {
//       console.log(document.text);
//       return document;
//     }
//   });
//   console.log("データ取り出した");
// }
