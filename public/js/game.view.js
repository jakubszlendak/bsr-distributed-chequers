(function (window) {
	'use strict';


	function GameView(model, controller) {
		console.log('GameView#constructor', model, controller)
		
		// find control elements
		var loginPanel = qs('#loginForm')
		var playerList = qs('#playerList')

		var loginBtn = qs('#loginButton')
		var registerBtn = qs('#registerButton')
		var usernameInput = qs('#loginInput')
		var passwdInput = qs('#passwdInput')


		// dont display player list until logged in
		playerList.style.display = 'none'


		model.addEventListener('boardChanged')

		model.addEventListener('login', function(user) {
			if(!user) alert('Login failed')
			else {
				alert('User ' + user.username + 'logged in.')
				loginPanel.style.display = 'none'
				controller.getPlayerList()
				
			}
		})

		model.addEventListener('register', function(user) {
			if(!user) alert('User already exists')
			else {
				alert('User ' + user.username + 'registered and logged in.')
				loginPanel.style.display = 'none'
				controller.getPlayerList()

			}
		})

		
		loginBtn.addEventListener('click', function (e) {
			// console.log('GameView#loginBtn:click')

			e.preventDefault()
			var login = usernameInput.value
			var pass = passwdInput.value

			controller.login(login, pass)
		}, false)

		registerBtn.addEventListener('click', function (e) {
			// console.log('GameView#registerBtn:click')
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