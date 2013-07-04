package Utilities;

public class Utilities {
	public static int[] cleanCommand(String command) {
		String temp = command;
		int[] ar = new int[4];
		
		temp = temp.replaceAll(" ", "");
		temp = temp.replaceAll("-", "");
		
		temp = temp.replaceAll("A", Integer.toString(commandEnum.A.value()));
		temp = temp.replaceAll("B", Integer.toString(commandEnum.B.value()));
		temp = temp.replaceAll("C", Integer.toString(commandEnum.C.value()));
		temp = temp.replaceAll("D", Integer.toString(commandEnum.D.value()));
		temp = temp.replaceAll("E", Integer.toString(commandEnum.E.value()));
		temp = temp.replaceAll("F", Integer.toString(commandEnum.F.value()));
		
		for (int i = 0; i < 4; i++) {
			ar[i] = Integer.parseInt(String.valueOf(temp.charAt(i)));
		}
		
		return ar;
	}
}
