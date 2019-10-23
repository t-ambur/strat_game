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
		
		public Clock()
		{
			ticking = false;
			paused = false;
			
			seconds = 0;
			minutes = 0;
			hours = 0;
			days = 0;
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
			gameDay++;
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
			String gameTime = "Year: " + gameYear + " Month: " + gameMonth + " Day: " + gameDay;
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
}
