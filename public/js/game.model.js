(function (window) {
	'use strict';

	/**
	 * Creates a new Model instance.
	 * @constructor
	 */
	function GameModel() {
		this.board = []
		this.user = null
		this.userLoggedIn = false
		this.userCreated = false
		this.playerList = []
		this.invitingPlayer = null
		this.invitedPlayer = null
		this.invitedPlayerDecision = false

		var eventEmitter = new EventEmitter()

		return {
			setUser: function(userCredentials) {
				this.user = userCredentials
				eventEmitter.emitEvent('userChanged', this.board)
			},

			setBoard: function(newBoard) {
				this.board = newBoard
				eventEmitter.emitEvent('boardChanged', this.board)
			},

			setUserLoggedIn: function(isLoggedIn) {
				this.userLoggedIn = isLoggedIn
				eventEmitter.emitEvent('login', [isLoggedIn? this.user : false])
			},

			setUserCreated: function(isCreated) {
				this.userCreated = isCreated
				eventEmitter.emitEvent('register', [isCreated? this.user : false])
			},

			setPlayerList: function(list) {
				this.playerList = list.filter( function (item) {
					return item.name !== this.user.username //filter yourself from list
				}, this)
				eventEmitter.emitEvent('playerList', [this.playerList])
			},

			setInvitingPlayer: function(playerName) {
				eventEmitter.emitEvent('playerInviting', [playerName])
			},

			setInvitedPlayer: function( player ) {
				this.invitedPlayer = player
				eventEmitter.emitEvent('invitedPlayer', [player])				
			},

			setInvitedPlayerDecision: function(decision) {
				this.invitedPlayerDecision = decision
				eventEmitter.emitEvent('invitedPlayerDecision', [decision])
			},

			addEventListener: function(event, handler) {
				eventEmitter.addListener(event, handler)
			},

		}
	}


	window.app = window.app || {};
	window.app.GameModel = GameModel;
})(window);
