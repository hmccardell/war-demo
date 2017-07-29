#Hayes' War Implementation

* Supports 2 - 4 players
* Provides options for verbosity and delay control
* War isn't fair. Cards are not divided evenly in the case of 3 players. 
* War between players means that:
    * only players whose cards match participate in the war by throwing cards to the pot
    * each participant throws a card face down
    * each participant throws a card face up, which are evaluated for war
    * winner takes all cards
* Changing the war rules so that more cards are played face down, such as three cards being played, would be a relatively simple change by changing adding more gather calls to the handler.
