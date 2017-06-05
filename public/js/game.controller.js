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
			var fields = event.data.split('#')
			if(fields.length) {
				if(fields[0]==='LGN') {
					model.setUserLoggedIn(!!+fields[1])
				}
				if(fields[0]==='CRA') {
					model.setUserCreated(!!+fields[1])
				}
				if(fields[0]==='LSP') {
					let users = new Array(Math.floor((fields.length-2)/2))
					console.log(users.length)
					for (var i = 1; i < fields.length; i++) {
						var element = fields[i];
						users[Math.floor(i/2)] = users[Math.floor(i/2)] || {}
						if(i%2) { //status
							users[Math.floor(i/2)].status = element
						} else {
							users[Math.floor(i/2)].name = element
						}
						
					}
					console.log(users)
					model.setPlayerList(users)
				}
			}
		}

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

	

	// Export to window
	window.app = window.app || {};
	window.app.GameController = GameController;
})(window);
