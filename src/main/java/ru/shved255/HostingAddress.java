package ru.shved255;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostingAddress {
    private String ip;
    private String port;
    private Pinger pinger;

    public HostingAddress(String ip, String port, int timeout) {
        this.ip = ip;
        this.port = port;
        this.pinger = new Pinger(ip, Integer.valueOf(port), timeout);
    }

    public boolean fetch(boolean timeout) {
        return this.pinger.fetchData(timeout);
    }

    public String getIp() {
        return this.ip;
    }

    public String getPort() {
        return this.port;
    }

    public Pinger getPinger() {
        return this.pinger;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (this.ip == null ? 0 : this.ip.hashCode());
        result = prime * result + (this.port == null ? 0 : this.port.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HostingAddress other = (HostingAddress) obj;
        if (this.ip == null ? other.ip != null : !this.ip.equals(other.ip)) {
            return false;
        }
        return this.port == null ? other.port == null : this.port.equals(other.port);
    }

    @Override
    public String toString() {
        return this.ip + ":" + this.port;
    }
    
    
    public static HostingAddress generateBoolean(Boolean flag, int timeout) {
    	int random = Choice.getRandomInt(0, 1);
    	if(random == 0) {
    		return generateHM(flag, timeout);
    	} else {
    		return generateBH(timeout);
    	}
    }
    public static HostingAddress generateHM(Boolean flag, int timeout) {
        String[] chars = {"m", "n", "f", "d", "w", "s", "z"};
        int number = Choice.getRandomInt(1, 32);
        String let = chars[Choice.getRandomInt(0, chars.length - 1)];
        String num = String.valueOf(number);
        int prt = Choice.getRandomInt(25000, 27000);
        String port = String.valueOf(prt);
        String[] ends = {"ru", "xyz"};
        String ip = flag == null ? let + num + ".joinserver." + ends[Choice.getRandomInt(0, 1)] 
                                  : (flag ? let + num + ".joinserver." + ends[0] : let + num + ".joinserver." + ends[1]);
        try {
            InetAddress.getByName(ip);
            return new HostingAddress(ip, port, timeout);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
    }

    public static HostingAddress generateCH(int timeout) {
        String begin = "45.93.200";
        String node = String.valueOf(Choice.getRandomInt(1, 30));
        String port = String.valueOf(Choice.getRandomInt(25000, 27000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateAU1(int timeout) {
        String begin = "116.202.48";
        String node = String.valueOf(Choice.getRandomInt(240, 240));
        String port = String.valueOf(Choice.getRandomInt(20000, 35000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateAU2(int timeout) {
        String begin = "49.12.82";
        String node = String.valueOf(Choice.getRandomInt(39, 39));
        String port = String.valueOf(Choice.getRandomInt(20000, 35000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateUS(int timeout) {
        String begin = "45.133.9";
        String node = String.valueOf(Choice.getRandomInt(167, 167));
        String port = String.valueOf(Choice.getRandomInt(25000, 28000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateZ(int timeout) {
        String begin = "5.188.118";
        String node = String.valueOf(Choice.getRandomInt(142, 142));
        String port = String.valueOf(Choice.getRandomInt(30000, 34000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    
    public static HostingAddress generateZ1(int timeout) {
        String begin = "92.53.65";
        String node = String.valueOf(Choice.getRandomInt(136, 136));
        String port = String.valueOf(Choice.getRandomInt(30000, 32000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateMS(int timeout) {
        String begin = "217.106.107";
        String node = String.valueOf(Choice.getRandomInt(176, 176));
        String port = String.valueOf(Choice.getRandomInt(25565, 32500));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateMS1(int timeout) {
        String begin = "212.22.93";
        String node = String.valueOf(Choice.getRandomInt(69, 69));
        String port = String.valueOf(Choice.getRandomInt(25000, 30000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateBH(int timeout) {
        int number = Choice.getRandomInt(1, 7);
        String num = String.valueOf(number);
        int prt = Choice.getRandomInt(25000, 29000);
        String port = String.valueOf(prt);
        String ip = "ru" + num + ".mineserv.su";
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateBH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    
    public static HostingAddress generateSR(int timeout) {
        String begin = "217.106.107";
        String node = String.valueOf(Choice.getRandomInt(111, 111));
        String port = String.valueOf(Choice.getRandomInt(20000, 21000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateGA(int timeout) {
        String begin = "65.108.206";
        String node = String.valueOf(Choice.getRandomInt(102, 102));
        String port = String.valueOf(Choice.getRandomInt(25000, 28000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateGA1(int timeout) {
        String begin = "65.108.227";
        String node = String.valueOf(Choice.getRandomInt(231, 231));
        String port = String.valueOf(Choice.getRandomInt(25000, 26000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateRE(int timeout) {
        String begin = "188.127.241";
        String node = String.valueOf(Choice.getRandomInt(8, 8));
        String port = String.valueOf(Choice.getRandomInt(25000, 26000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateOU(int timeout) {
        String begin = "95.217.79";
        String node = String.valueOf(Choice.getRandomInt(25, 25));
        String port = String.valueOf(Choice.getRandomInt(25500, 26000));
        String ip = begin + "." + node; 
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return HostingAddress.generateCH(timeout);
        }
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }

    public static HostingAddress generate(Boolean flag, int timeout) {	
        int randomChoice = Choice.getRandomInt(0, 14);
        if(randomChoice == 0) {
        	return HostingAddress.generateHM(flag, timeout);
        } else if(randomChoice == 1) {
        	return HostingAddress.generateCH(timeout);   
        } else if(randomChoice == 2) {
        	return HostingAddress.generateBH(timeout);
        } else if(randomChoice == 3) {
        	return HostingAddress.generateAU1(timeout); 
        } else if(randomChoice == 4) {
        	return HostingAddress.generateAU2(timeout);
        } else if(randomChoice == 5) {
        	return HostingAddress.generateUS(timeout);
        } else if(randomChoice == 6) {
        	return HostingAddress.generateZ(timeout);
        } else if(randomChoice == 7) { 
        	return HostingAddress.generateZ1(timeout);  
        } else if(randomChoice == 8) {
        	return HostingAddress.generateMS(timeout);  
        } else if(randomChoice == 9) {
        	return HostingAddress.generateMS1(timeout); 
        } else if(randomChoice == 10) {
        	return HostingAddress.generateSR(timeout); 
        } else if(randomChoice == 11) {
        	return HostingAddress.generateGA(timeout);  
        } else if(randomChoice == 12) {
        	return HostingAddress.generateGA1(timeout); 
        } else if(randomChoice == 13) {
        	return HostingAddress.generateRE(timeout);  
        } else if(randomChoice == 14) {
           	return HostingAddress.generateOU(timeout);   
        }
        return HostingAddress.generateHM(flag, timeout);
    }
    public static HostingAddress generateHMOp(Boolean flag, int timeout) {
        String[] chars = {"m", "n", "f", "d", "w", "s", "z"};
        int number = Choice.getRandomInt(1, 32);
        String let = chars[Choice.getRandomInt(0, chars.length - 1)];
        String num = String.valueOf(number);
        int prt = Choice.getRandomInt(25000, 27000);
        String port = String.valueOf(prt);
        String[] ends = {"ru", "xyz"};
        String ip = flag == null ? let + num + ".joinserver." + ends[Choice.getRandomInt(0, 1)] 
        			: (flag ? let + num + ".joinserver." + ends[0] : let + num + ".joinserver." + ends[1]);
        return new HostingAddress(ip, port, timeout);
    }
    public static HostingAddress generateAU1Op(int timeout) {
        String begin = "116.202.48";
        String node = String.valueOf(Choice.getRandomInt(240, 240));
        String port = String.valueOf(Choice.getRandomInt(20000, 35000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateAU2Op(int timeout) {
        String begin = "49.12.82";
        String node = String.valueOf(Choice.getRandomInt(39, 39));
        String port = String.valueOf(Choice.getRandomInt(20000, 35000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateZOp(int timeout) {
        String begin = "5.188.118";
        String node = String.valueOf(Choice.getRandomInt(142, 142));
        String port = String.valueOf(Choice.getRandomInt(30000, 34000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateMSOp(int timeout) {
        String begin = "217.106.107";
        String node = String.valueOf(Choice.getRandomInt(176, 176));
        String port = String.valueOf(Choice.getRandomInt(25565, 32500));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateMS1Op(int timeout) {
        String begin = "212.22.93";
        String node = String.valueOf(Choice.getRandomInt(69, 69));
        String port = String.valueOf(Choice.getRandomInt(25000, 30000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    
    public static HostingAddress generateSROp(int timeout) {
        String begin = "217.106.107";
        String node = String.valueOf(Choice.getRandomInt(111, 111));
        String port = String.valueOf(Choice.getRandomInt(20000, 21000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateGAOp(int timeout) {
        String begin = "65.108.206";
        String node = String.valueOf(Choice.getRandomInt(102, 102));
        String port = String.valueOf(Choice.getRandomInt(25000, 28000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateGA1Op(int timeout) {
        String begin = "65.108.227";
        String node = String.valueOf(Choice.getRandomInt(231, 231));
        String port = String.valueOf(Choice.getRandomInt(25000, 26000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateREOp(int timeout) {
        String begin = "188.127.241";
        String node = String.valueOf(Choice.getRandomInt(8, 8));
        String port = String.valueOf(Choice.getRandomInt(25000, 26000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }
    public static HostingAddress generateOUOp(int timeout) {
        String begin = "95.217.79";
        String node = String.valueOf(Choice.getRandomInt(25, 25));
        String port = String.valueOf(Choice.getRandomInt(25500, 26000));
        String ip = begin + "." + node; 
        HostingAddress adr = new HostingAddress(ip, port, timeout);
        return adr;
    }

    public static HostingAddress generateOp(Boolean flag, int timeout) {	
        int randomChoice = Choice.getRandomInt(0, 10);
        if(randomChoice == 0) {
        	return HostingAddress.generateHMOp(flag, timeout);
        } else if(randomChoice == 1) {
        	return HostingAddress.generateAU1Op(timeout); 
        } else if(randomChoice == 2) {
        	return HostingAddress.generateAU2Op(timeout);
        } else if(randomChoice == 3) {
        	return HostingAddress.generateZOp(timeout);
        } else if(randomChoice == 4) { 
        	return HostingAddress.generateMSOp(timeout);  
        } else if(randomChoice == 5) {
        	return HostingAddress.generateMS1Op(timeout); 
        } else if(randomChoice == 6) {
        	return HostingAddress.generateSROp(timeout); 
        } else if(randomChoice == 7) {
        	return HostingAddress.generateGAOp(timeout);  
        } else if(randomChoice == 8) {
        	return HostingAddress.generateGA1Op(timeout); 
        } else if(randomChoice == 9) {
        	return HostingAddress.generateREOp(timeout);  
        } else if(randomChoice == 10) {
           	return HostingAddress.generateOUOp(timeout);   
        }
        return HostingAddress.generateHMOp(flag, timeout);
    }
}
