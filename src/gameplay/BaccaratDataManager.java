package gameplay;

import gamecardbaccarat.SetGameBaccarat;


public class BaccaratDataManager extends SetGameBaccarat {
    public BaccaratDataManager(int numberOfPlayer, int initialMoney) {
        setNumberOfPersons(numberOfPlayer);
        setMoney(initialMoney);
        addPlayer();
    }

}
