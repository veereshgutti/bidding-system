# Bidding-System

bidding system facilitates bidding & selling of listed items to registered users. user can put an item for sale which can be bought by other users through bidding.An item is sold to the highest bidder on expiry of bidding time or on desired price.
  - User can put an item for sale
  - Only Registered user can bid for item
  - Bid expiry is time should be in Minutes
  - After bid timeout user with lowest bidding price will win
  - Item will be removed, if no user bid within timeout
  - User cannot bid removed Item
  - User cannot bid with price lower than item base price
  - User can view bidder to perticuler item, sorted by lowest price
  - Multiple users can bid for one item

### Installation
```sh
$ git clone [git-repo-url]
$ cd bidding-system
$ gradle build
```
