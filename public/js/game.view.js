/* global qs, qsa, Handlebars */
(function (window) {
	'use strict';


	function GameView(model, controller) {
		console.log('GameView#constructor', model, controller)

		// find control elements
		var loginPanel = qs('#loginForm')
		var playerList = qs('#playerList')
		var notificationBar = qs('#notificationBar')
		var gameBoard = qs('#gameBoard')
		var navbar = qs('#navbar')

		var playerListTemplate = Handlebars.compile(playerList.textContent)
		var notificationBarTemplate = Handlebars.compile(notificationBar.textContent)
		var gameBoardTemplate = Handlebars.compile(gameBoard.textContent)
		var navbarTemplate = Handlebars.compile(navbar.textContent)

		var loginBtn = qs('#loginButton')
		var registerBtn = qs('#registerButton')
		var usernameInput = qs('#loginInput')
		var passwdInput = qs('#passwdInput')

		function setupUserUI(user) {
			renderItem('#navbar', navbarTemplate, {
				loggedIn: true,
				username: user.username
			})
			loginPanel.style.display = 'none'
			controller.getPlayerList()

			controller.pollPlayerList()
		}

		model.addEventListener('login', function (user) {
			if (!user) {
				renderItem('#notificationBar', notificationBarTemplate, {
					clazz: 'danger',
					message: 'Login failed!'
				})
			} else {
				renderNotification('#notificationBar', notificationBarTemplate, {
					clazz: 'success',
					message: 'User ' + user.username + ' logged in.'
				})
				setupUserUI(user)

			}
		})

		model.addEventListener('register', function (user) {
			if (!user) {
				renderItem('#notificationBar', notificationBarTemplate, {
					clazz: 'danger',
					message: 'User already exists!'
				})
				renderItem('#navbar', navbarTemplate, {
					loggedIn: true,
					username: user.username
				})
			} else {
				renderNotification('#notificationBar', notificationBarTemplate, {
					clazz: 'success',
					message: 'User ' + user.username + ' registered and logged in.'
				})
				setupUserUI(user)

			}
		})

		model.addEventListener('playerList', function (list) {
			renderItem('#playerList', playerListTemplate, {
				players: list
			})
			list.forEach(function (player) {
				var playBtn = qs('#' + player.name + '_-_-_button')
				if (playBtn) playBtn.addEventListener('click', function (e) {
					var playerName = e.target.id.split('_-_-_')[0]
					controller.requestGame(playerName)
				})
			})
		})

		model.addEventListener('playerInviting', function (playerName) {
			var message = 'Player ' + playerName + ' wants to play a game with you. Do you agree?'
			var decision = confirm(message)
			controller.respondForGameRequest(decision)
		})

		model.addEventListener('invitedPlayer', function (playerName) {
			renderItem('#notificationBar', notificationBarTemplate, {
				clazz: 'info',
				message: 'Waiting for ' + playerName + ' response...'
			})
		})

		model.addEventListener('invitedPlayerDecision', function (decision) {
			if (decision) {
				//start game
				hideItem('#notificationBar')
			} else {
				renderItem('#notificationBar', notificationBarTemplate, {
					clazz: 'danger',
					message: 'Player ' + model.invitedPlayer + ' does not want to play with you :('
				})
			}
		})

		model.addEventListener('gameStarted', function () {
			controller.stopPollingPlayerList()
			hideItem('#playerList')
		})

		model.addEventListener('boardChanged', function () {
			renderBoard('#gameBoard', gameBoardTemplate, model, controller)
		})

		model.addEventListener('playerMove', function () {
			renderBoard('#gameBoard', gameBoardTemplate, model, controller)			
		})

		loginBtn.addEventListener('click', function (e) {
			e.preventDefault()
			var login = usernameInput.value
			var pass = passwdInput.value

			controller.login(login, pass)
		}, false)

		registerBtn.addEventListener('click', function (e) {
			e.preventDefault()
			var login = usernameInput.value
			var pass = passwdInput.value

			controller.register(login, pass)
		})
	}

	// Export to window
	window.app = window.app || {};
	window.app.GameView = GameView;
}(window));


function renderBoard(idSelector, template, model, controller) {
	var boardData = {
		playerColor: model.playerColor, 
		rows: model.board, 
		yourMove: model.playerMove
	}
	renderItem(idSelector, template, boardData)
	console.log('.checker.'+model.playerColor)


	var checkerToMove = null

	qsa('.checker.'+model.playerColor).forEach(function(element){
		element.addEventListener('click', function(e){
			markChecker('#'+element.id)
			var coords = element.id.split('_')[1].split('-')
			checkerToMove = [ +coords[1] , +coords[0] ]
		})
	})

	qsa('.field.empty').forEach(function (element) {
		element.addEventListener('click', function(e){
			if(checkerToMove){
				var coords = element.id.split('_')[1].split('-')
				var destination = [ +coords[1], +coords[0]  ]
				controller.moveChecker(checkerToMove, destination)
			}
		})
	})
}

function renderItem(idSelector, template, data) {
	var itemHTML = template(data)
	var itemElem = document.createElement('div')
	itemElem.id = idSelector.replace('#', '') // remove hash from selector

	itemElem.innerHTML = itemHTML
	qs('body').replaceChild(itemElem, qs(idSelector))
}

function hideItem(idSelector) {
	qs(idSelector).style.display = 'none'
}


function renderNotification(idSelector, template, data, duration) {
	renderItem(idSelector, template, data)
	setTimeout(function() {
		hideItem(idSelector)
	}, duration || 5000)
}

function markChecker(selector) {
	console.log('markChecker', qs(selector))
	qsa('.checker').forEach(function(e) {e.setAttribute('style', 'box-shadow: none;')})
	qs(selector).setAttribute('style', 'box-shadow: 0px 0px 3px 4px red;')
}