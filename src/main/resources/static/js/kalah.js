var stompClient = null;
var gameId = null;
var player1 = null;
var player2 = null;

function connect() {
    var socket = new SockJS("/kalah-websocket");
    stompClient = Stomp.over(socket);
    sessionStorage.id = Math.random().toString(36);
    console.log("Generated random id for game " + sessionStorage.id);
    stompClient.connect({}, function (frame) {
      stompClient.subscribe("/topic/game/new/" + sessionStorage.id, function (game) {
        displayGameStatus(JSON.parse(game.body));
        newGame(JSON.parse(game.body));
      });
      stompClient.subscribe("/topic/player/new/" + sessionStorage.id, function (player) {
        setPlayerId(JSON.parse(player.body));
      });
      stompClient.subscribe("/topic/game/sow/" + sessionStorage.id, function (game) {
        displayGameStatus(JSON.parse(game.body));
        displayCurrentGame(JSON.parse(game.body));
      });
      afterConnected(true);
    });
}

function sendName() {
  stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}
function sendNewGame() {
  stompClient.send('/app/game/' + sessionStorage.id, {}, JSON.stringify([player1, player2 ]));
}
function sendNewPlayer(playerName, playerTurn) {
  stompClient.send('/app/player/' + sessionStorage.id, {}, JSON.stringify({'name': playerName, 'turn': playerTurn}));
}
function sendMove(playerId, pitIndex) {
  stompClient.send('/app/sow/' + sessionStorage.id, {}, JSON.stringify({'gameId': gameId, 'playerId': playerId, 'pitIndex':pitIndex}));
}

function afterConnected(connected) {
  sendNewPlayer($( "#player1name" ).val(), "FIRST");
  sendNewPlayer($( "#player2name" ).val(), "SECOND");
  $("#player1name").val("");
  $("#player2name").val("");
  $("#startGame").removeAttr("disabled");
  $("#sendPlayers").attr("disabled","disabled");
}

function displayCurrentGame(game) {
  console.log(game);
  for (j=0;j<6;j++) {
    replaceElementText("p1"+"b"+(j),game.board.player1.pits[j].numberOfStones);
  }
  for (j=0;j<6;j++) {
    replaceElementText("p2"+"b"+(j),game.board.player2.pits[j].numberOfStones);
  }
  replaceElementText("p1store", game.board.player1.store.numberOfStones);
  replaceElementText("p2store", game.board.player2.store.numberOfStones);
}

function displayGameStatus(game) {
  if (game.gameStatus == "GAME_OVER") {
    displayGameOver(game);
  } else if (game.playersTurn == "FIRST") {
    replaceElementText("gameStatus", "Turn: " + game.board.player1.name);
  } else {
    replaceElementText("gameStatus", "Turn: " + game.board.player2.name);
  }
}

function displayGameOver(game) {
  if (game.board.player1.store.numberOfStones > game.board.player2.store.numberOfStones) {
    replaceElementText("gameStatus", "Game Over! Winner is: " + game.board.player1.name);
  } else if (game.board.player2.store.numberOfStones > game.board.player1.store.numberOfStones) {
    replaceElementText("gameStatus", "Game Over! Winner is: " + game.board.player2.name);
  } else {
    replaceElementText("gameStatus", "Game Over! It was a tie.");
  }
  $("#startGame").removeAttr("disabled");
  $("#sendPlayers").removeAttr("disabled");
}

function setPlayerId(player) {
  if(player.turn == "FIRST"){
    player1 = player;
  } else {
    player2 = player;
  }
}

function newGame(game) {
  gameId = game.id;
  displayCurrentGame(game);
  $('#startGame').attr('disabled','disabled');
}

function replaceElementText(elementId, text){
  $( document.getElementById(elementId) ).text(text);
}

$(function () {
  $( "#sendPlayers" ).click(function() { connect(); });
  $( "#startGame" ).click(function() { sendNewGame(); });
  $( "#p1b5" ).click(function() { sendMove(player1.id, 5); });
  $( "#p1b4" ).click(function() { sendMove(player1.id, 4); });
  $( "#p1b3" ).click(function() { sendMove(player1.id, 3); });
  $( "#p1b2" ).click(function() { sendMove(player1.id, 2); });
  $( "#p1b1" ).click(function() { sendMove(player1.id, 1); });
  $( "#p1b0" ).click(function() { sendMove(player1.id, 0); });
  $( "#p2b5" ).click(function() { sendMove(player2.id, 5); });
  $( "#p2b4" ).click(function() { sendMove(player2.id, 4); });
  $( "#p2b3" ).click(function() { sendMove(player2.id, 3); });
  $( "#p2b2" ).click(function() { sendMove(player2.id, 2); });
  $( "#p2b1" ).click(function() { sendMove(player2.id, 1); });
  $( "#p2b0" ).click(function() { sendMove(player2.id, 0); });
});
