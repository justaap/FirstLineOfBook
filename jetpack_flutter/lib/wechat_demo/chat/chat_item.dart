class ChatItem {
  final String name;
  final String message;
  final String imageUrl;

  ChatItem(this.name,this.message,this.imageUrl);

  factory ChatItem.fromMap(Map map){
    return ChatItem(map['name'],  map["message"], map["imageUrl"]);
  }
}
