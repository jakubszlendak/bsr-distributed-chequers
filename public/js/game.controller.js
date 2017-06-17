(function (window) {
	'use strict';

	/**
	 * Takes a model and view and acts as the controller between them
	 *
	 * @constructor
	 * @param {object} model The model instance
	 * @param {object} view The view instance
	 */
	function GameController(model) {
		var socket = new WebSocket('ws://'+window.location.host+'/')

		socket.onopen = function(event) {
			console.log('GameController#socket:onopen ', event)
		}

		socket.onerror = function(event) {
			console.log('GameController#socket:onerror', event)
		}

		socket.onclose = function(event) {
			console.log('GameController#socket:onclose', event)
		}

		socket.onmessage = function(event) {
			console.log('GameController#socket:onmessage', event.data)
			// deal with 'sticky' messages
			var messages = event.data.split('\n').filter( function(msg) { return !!msg })

			// if there are many messages sticked together, defer handling
			if(messages.length > 1) {
				messages.slice(1).forEach(function (message) {
					setTimeout(function () {
						socket.onmessage.call(this, { data: message })
					})
				})
			}

			var fields = messages[0].split('#')
			if(fields.length) {

				// login - response
				if(fields[0]==='LGN') {
					model.setUserLoggedIn(!!+fields[1])
				}
				// create account - response
				if(fields[0]==='CRA') {
					model.setUserCreated(!!+fields[1])
				}
				// list players - response
				if(fields[0]==='LSP') {
					var rawUsers = fields.slice(1, fields.length)
					var users = new Array(rawUsers.length / 2)
					for (var i = 0; i < rawUsers.length; i++) {
						var element = rawUsers[i];
						var userIndex = Math.floor(i/2)
						users[userIndex] = users[userIndex] || {}
						if(i%2) { //status (A or B)
							users[userIndex].status = (element === 'A')
						} else { //name
							users[userIndex].name = element
						}	
					}
					model.setPlayerList(users)
				}
				// request play - someone invites you
				if(fields[0]==='RP1') {
					var opponentName = fields[1]
					model.setInvitingPlayer(opponentName)
				}
				// response for your invite 
				if(fields[0]==='RP2') {
					var opponentDecision = +fields[1]
					model.setInvitedPlayerDecision(opponentDecision)
				}
				//game init
				if(fields[0]==='INI') {
					var playerColor = fields[1]
					model.setGameStarted(playerColor)
				}
				//board
				if(fields[0]==='CHB') {
					var board = decodeBoard(fields[1])
					model.setBoard(board)
				}

			}
		}

		//methods called by view
		var apiPrototype = {
			login: function login(username, password) {
				if(socket.readyState === WebSocket.OPEN) {
					model.setUser({username: username, password: password})
					socket.send(encodeMessage('LGN',[username, password]))
				}
			},
			register: function register(username, password) {
				if(socket.readyState === WebSocket.OPEN) {
					model.setUser({username: username, password: password})					
					socket.send(encodeMessage('CRA',[username, password]))
				}
			},
			getPlayerList: function() {
				if(socket.readyState === WebSocket.OPEN) {				
					socket.send(encodeMessage('LSP'))
				}
			},

			pollPlayerList: function() {
				if(this.playerListPoll) clearInterval(this.playerListPoll)

				this.playerListPoll = setInterval(function(){
					this.getPlayerList()
				}.bind(this), 10000)
			},

			stopPollingPlayerList: function() {
				if(this.playerListPoll) clearInterval(this.playerListPoll)
			},

			requestGame:  function(opponentName) {
				if(socket.readyState === WebSocket.OPEN) {	
					model.setInvitedPlayer(opponentName)			
					socket.send(encodeMessage('RFP', [opponentName]))
				}
			},
			respondForGameRequest: function(decision) {
				if(socket.readyState === WebSocket.OPEN) {				
					socket.send(encodeMessage('RP1', [+decision]))
				}
			}

		}

		return Object.create(apiPrototype)

	}
	


	function encodeMessage(command, args) {
		if (args) args.forEach(function(element) {
			command+= '#' + element
		})
		return command + '\n'
	}

	function decodeBoard(data) {
		var board = new Array(8)
		for (var i = 0; i < 8; i++) {
			board[i] = data.slice(i, i+8).split('')
		}
		return board
	}

	

	// Export to window
	window.app = window.app || {};
	window.app.GameController = GameController;
})(window);
