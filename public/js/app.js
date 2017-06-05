/*global app */
(function () {
	'use strict';

	var gameModel = new app.GameModel()
	var gameController = new app.GameController(gameModel)
	var gameView = new app.GameView(gameModel, gameController)
	

})();
