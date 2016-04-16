package carlosdelachica.infrastructure;

import java.util.Date;

public class Clock {

  public long currentTimeInMillis() {
    return new Date().getTime();
  }
}
