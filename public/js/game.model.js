/* global EventEmitter */
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
		this.playerColor = null
		this.eventLog = []
		this.eventEmitter = new EventEmitter()


	}
	GameModel.prototype.setUser = function (userCredentials) {
			this.user = userCredentials
			this.eventEmitter.emitEvent('userChanged', [this.user])
		},

		GameModel.prototype.setBoard= function (newBoard) {
			this.board = newBoard
			this.playerMove = false
			this.eventEmitter.emitEvent('boardChanged', [this.board])
		}

		GameModel.prototype.setUserLoggedIn= function (isLoggedIn) {
			this.userLoggedIn = isLoggedIn
			this.eventEmitter.emitEvent('login', [isLoggedIn ? this.user : false])
		}

		GameModel.prototype.setUserCreated= function (isCreated) {
			this.userCreated = isCreated
			this.eventEmitter.emitEvent('register', [isCreated ? this.user : false])
		}

		GameModel.prototype.setPlayerList= function (list) {
			this.playerList = list.filter(function (item) {
				return item.name !== this.user.username //filter yourself from list
			}, this)
			this.eventEmitter.emitEvent('playerList', [this.playerList])
		}

		GameModel.prototype.setInvitingPlayer= function (playerName) {
			this.invitingPlayer = playerName
			this.opponentName = playerName
			this.eventEmitter.emitEvent('playerInviting', [this.invitingPlayer])
		}

		GameModel.prototype.setInvitedPlayer= function (playerName) {
			this.invitedPlayer = playerName
			this.opponentName = playerName
			this.eventEmitter.emitEvent('invitedPlayer', [this.invitedPlaye])
		}

		GameModel.prototype.setInvitedPlayerDecision= function (decision) {
			this.invitedPlayerDecision = decision
			this.eventEmitter.emitEvent('invitedPlayerDecision', [this.invitedPlayerDecision])
		}

		GameModel.prototype.setGameStarted= function (color) {
			this.playerColor = color
			this.eventEmitter.emitEvent('gameStarted')
		}

		GameModel.prototype.setPlayerMove= function (isYourMove) {
			this.playerMove = isYourMove
			this.eventEmitter.emitEvent('playerMove', [this.playerMove])
		}

		GameModel.prototype.setGameResult= function (winner, reason) {
			this.winner = winner
			this.playerWon = winner === this.user.username
			this.gameEndReason = reason
			this.eventEmitter.emitEvent('endOfGame')
		}


		GameModel.prototype.logEvent= function (event, level) {
			this.eventLog.push({
				date: new Date(),
				event: event, 
				level: level || 'log'
			})
			if(this.eventLog.length>5) this.eventLog = this.eventLog.slice(this.eventLog.length - 5, this.eventLog.length)
			this.eventEmitter.emitEvent('eventLogged', [this.eventLog])	
		}

		GameModel.prototype.clearLog= function () {
			this.eventLog = []
		}

		GameModel.prototype.addEventListener= function (event, handler) {
			this.eventEmitter.addListener(event, handler)
		}


		window.app = window.app || {};
	window.app.GameModel = GameModel;
})(window);