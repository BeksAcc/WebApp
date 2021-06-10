package databean;

public class ItemBean {
    private int    id;
	private String item;
	private int    position;
	private String ipAddress;
	private String userName;
	
	public int    getId()                { return id;           }
    public String getItem()              { return item;         }
    public int    getPosition()          { return position;     }
    public String getIpAddress()         { return ipAddress;    }
    public String getUserName()          { return userName;     }

    public void   setId(int i)           { id = i;              }
	public void   setItem(String s)      { item = s;            }
	public void   setPosition(int i)     { position = i;        }
	// @MaxSize(50)
	// public void   setIpAddress(String s) { ipAddress = s;       }
	// @MaxSize(50)
	// public void   setUserName(String s)  { userName = s;        }
}