# DeckGame API

## This is an API that handles a Simple DeckGame.
## You can:

## create a game: 
    -> POST HOST/games/create
## delete a game: 
    -> DELETE HOST/games/delete/{gameId}
## list all the games:
    -> GET HOST/games/list
## list data from a game:
    -> GET HOST/games/{gameId}
## create a deck linking it to a game: 
    -> POST HOST/games/{gameId}/create-deck
## add a deck to a gamedeck: 
    -> POST HOST/games/{gameId}/add-deck/{deckId}
## add a player into a game: 
    -> POST HOST/games/{gameId}/add-player
## remove a player from a game 
    -> DELETE HOST/games/{gameId}/remove-player/{playerId}
## deal cards to all players in a game 
    -> POST HOST/games/{gameId}/deal
## list the cards of a player 
    -> GET HOST/games/{gameId}/{playerId}/cards
## list a rank of players considering the face values of their cards 
    -> GET HOST/games/{gameId}/players
## count cards of each suit 
    -> GET HOST/games/{gameId}/count-suits
## cout cards of each suit-face, in face descending order 
    -> GET HOST/games/{gameId}/count-cards
## shuffle the gamedeck 
    -> POST HOST/games/shuffle/{gameId}


