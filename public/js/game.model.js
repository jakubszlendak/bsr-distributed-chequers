(function (window) {
	'use strict';

	/**
	 * Creates a new Model instance.
	 * @constructor
	 */
	function GameModel() {
		var board = []
		var user = null
		var userLoggedIn = false
		var userCreated = false
		var playerList = []

		var eventEmitter = new EventEmitter()

		return {
			setUser: function(userCredentials) {
				user = userCredentials
				eventEmitter.emitEvent('userChanged', board)
			},

			setBoard: function(newBoard) {
				board = newBoard
				eventEmitter.emitEvent('boardChanged', board)
			},

			setUserLoggedIn: function(isLoggedIn) {
				userLoggedIn = isLoggedIn
				eventEmitter.emitEvent('login', [isLoggedIn? user : false])
			},

			setUserCreated: function(isCreated) {
				userCreated = isCreated
				eventEmitter.emitEvent('register', [isCreated? user : false])
			},

			setPlayerList: function(list) {
				playerList = list
				eventEmitter.emitEvent('playerList', [list])
			},

			addEventListener: function(event, handler) {
				eventEmitter.addListener(event, handler)
			},

		}
	}


	window.app = window.app || {};
	window.app.GameModel = GameModel;
})(window);
