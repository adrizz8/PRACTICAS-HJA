package model;

public enum Palo {
 h,d,c,s;

public static Palo valueOf(char charAt) {
	switch(charAt) {
		case 'h':
			return Palo.h;
		case 'd':
			return Palo.d;
		case 'c':
			return Palo.c;
		case 's':
			return Palo.s;
		}
	return null;
	}
}
