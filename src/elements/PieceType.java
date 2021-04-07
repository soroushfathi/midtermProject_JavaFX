package elements;


public enum PieceType {
    RED(1), WHITE(-1);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
