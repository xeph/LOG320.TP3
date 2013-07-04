package Utilities;

public enum commandEnum {
	A(0){
		@Override
		public int value() {
			return 0;
		}
	}, B(1){
		@Override
		public int value() {
			return 1;
		}
	}, C(2){
		@Override
		public int value() {
			return 2;
		}
	}, D(3){
		@Override
		public int value() {
			return 3;
		}
	}, E(4){
		@Override
		public int value() {
			return 4;
		}
	}, F(5){
		@Override
		public int value() {
			return 5;
		}
	};
	private int value;
	
	public abstract int value();
	
	private commandEnum(int value) {
		this.value = value;
	}

};
