package source.support;

public class Clock {
	// used for the purpose of measuring game time
	
		private boolean ticking;
		private boolean paused;
	
		private int seconds;
		private int minutes;
		private int hours;
		private int days;
		
		private int gameDay;
		private int gameMonth;
		private int gameYear;
		
		private int tick_speed;
		
		public static final int MAX_TICK_SPEED = 30;
		
		public Clock()
		{
			ticking = false;
			paused = false;
			
			seconds = 0;
			minutes = 0;
			hours = 0;
			days = 0;
			
			tick_speed = 1;
			
			gameDay = 1;
			gameMonth = 1;
			gameYear = 0;
		}
		
		public void start()
		{
			if (!ticking)
			{
				ticking = true;
				paused = false;
			}
		}
		
		public void pause()
		{
			if (!paused)
			{
				ticking = false;
				paused = true;
			}
		}
		
		public void togglePause()
		{
			if (paused)
			{
				start();
			}
			else
			{
				pause();
			}
		}
		
		public void reset()
		{
			ticking = false;
			paused = false;
			
			seconds = 0;
			minutes = 0;
			hours = 0;
			days = 0;
		}
		
		public void elapseSec()
		{
			if (ticking && !paused)
			{
				elapseGameDay();
				seconds++;
				if (seconds >= 60)
				{
					minutes++;
					seconds = 0;
					if (minutes >= 60)
					{
						hours++;
						minutes = 0;
						if (hours >= 24)
						{
							days++;
							hours = 0;
						}
					}
				}
			}
		}
		
		public void elapseGameDay()
		{
			gameDay += tick_speed;
			if (gameDay >= 31)
			{
				gameMonth++;
				gameDay = 0;
				if (gameMonth >= 13)
				{
					gameYear++;
					gameMonth = 0;
				}
			}
		}
		
		public boolean increaseSpeed()
		{
			if (tick_speed < MAX_TICK_SPEED)
			{
				tick_speed++;
				return true;
			}
			return false;
		}
		
		public boolean decreaseSpeed()
		{
			if (tick_speed > 1)
			{
				tick_speed--;
				return true;
			}
			return false;
		}
		
		public int getTickSpeed()
		{
			return tick_speed;
		}
		
		public void printTime()
		{
			System.out.println(toString());
		}
		
		public boolean equals(Object obj)
		{
			if (obj instanceof Clock && obj != null)
			{
				Clock c = (Clock) obj;
				return (days == c.getDays() && hours == c.getHours() && minutes == c.getMinutes() && seconds == c.getSeconds());
			}
			return false;
		}
		
		public String toString()
		{
			return "Day " + days + ". Current Time: " + hours + ":" + minutes + ":"  + seconds;
		}
		
		public String toStringGame()
		{
			String gameTime = "";
			if (paused)
			{
				gameTime = "|| ";
			}
			else
			{
				gameTime = "> ";
			}
			gameTime += "Speed:" + tick_speed + " Year: " + gameYear + " Month: " + gameMonth + " Day: " + gameDay;
			return gameTime;
		}

		public int getSeconds() {
			return seconds;
		}

		public int getMinutes() {
			return minutes;
		}

		public int getHours() {
			return hours;
		}

		public int getDays() {
			return days;
		}
		
		public boolean isTicking()
		{
			return ticking;
		}
		
		public boolean isPaused()
		{
			return paused;
		}
		
		public int getGameDay()
		{
			return gameDay;
		}
		
		public int getGameMonth()
		{
			return gameMonth;
		}
		
		public int getGameYear()
		{
			return gameYear;
		}
}
