package dentaku;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dentaku {
	/****************************************
	* 			mainメソッド				*
	****************************************/
	public static void main(String args[]) {
		Contents contents = new Contents("電卓", 100, 100, 315, 550);
		contents.setVisible(true); //フレームの可視化。
	}
}

class Contents extends JFrame implements ActionListener, KeyListener {
	/****************************************
	* 			ボタンの作成 				*
	****************************************/
	JButton button0 = new JButton("０");
	JButton button1 = new JButton("１");
	JButton button2 = new JButton("２");
	JButton button3 = new JButton("３");
	JButton button4 = new JButton("４");
	JButton button5 = new JButton("５");
	JButton button6 = new JButton("６");
	JButton button7 = new JButton("７");
	JButton button8 = new JButton("８");
	JButton button9 = new JButton("９");
	JButton buttonReverse = new JButton("±");
	JButton buttonEqual = new JButton("＝");
	JButton buttonPlus = new JButton("＋");
	JButton buttonMinus = new JButton("－");
	JButton buttonDot = new JButton("．");
	JButton buttonMultiply = new JButton("×");
	JButton buttonDivide = new JButton("÷");
	JButton buttonClear = new JButton("Ｃ");
	JButton buttonClearentry = new JButton("CE");
	JButton buttonBack = new JButton("Bc");
	JButton buttonPercent = new JButton("％");
	JButton buttonRoot = new JButton("√");
	JButton buttonJijyo = new JButton("x²");
	JButton buttonSomeshingPer1 = new JButton("1/x");

	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();

	JPanel p = new JPanel(); //パネルPを作成。

	//フラグ各種。
	boolean errorFlag = false, enzanFlag = false, dotFlag = false,
			minusFlag = false, foreDotFlag = false;
	//配列カウント変数各種。
	byte tmpValCount = 0, valSequenceCount = 0;
	//数値各種。
	double val = 0, subtotal = 0, foreVal = 0;
	//数値が整数の時に、小数点以下の０を表示させない為の整数数値各種。
	long valLong, subtotalLong = 0;
	//Bcボタン使用時のバックスペース用入力値保持配列。
	char tmpVal[] = new char[100];
	//計算の流れを保持する配列。
	String valSequence[] = new String[200];

	/****************************************
	* 		グローバル変数の宣言ここまで	*
	****************************************/

	public Contents(String title, int x, int y, int width, int height) { //仮引数は、フレームの配置位置横軸、縦軸。フレームのサイズ横軸、縦軸。
		setTitle(title); //フレームのタイトルをセット。
		setSize(width, height);
		setLocationRelativeTo(null);//フレームを中央に配置させる。
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //×ボタンが押された時に終了する。
		setResizable(false); // フレームのサイズを固定する。

		panel(); //パネル精製メソッドの呼び出し。
		tmpVal[0] = '0';
		showLabel1();
	}

	void panel() {

		p.setLayout(null); //パネルのデフォルトレイアウトの無効化。自由にレイアウト出来る様にする。
		/****************************************
		 * 		ボタンの位置とサイズの設定 		*
		 ****************************************/
		buttonReverse.setBounds(10, 460, 65, 50); //下から１段目、左から１番目に配置。
		button0.setBounds(85, 460, 65, 50);
		buttonDot.setBounds(160, 460, 65, 50);
		buttonEqual.setBounds(235, 460, 65, 50); //下から１段目、左から４番目に配置。

		button1.setBounds(10, 405, 65, 50); //下から２段目、左から１番目に配置。
		button2.setBounds(85, 405, 65, 50);
		button3.setBounds(160, 405, 65, 50);
		buttonPlus.setBounds(235, 405, 65, 50); //下から２段目、左から４番目に配置。

		button4.setBounds(10, 350, 65, 50); //下から３段目、左から１番目に配置。
		button5.setBounds(85, 350, 65, 50);
		button6.setBounds(160, 350, 65, 50);
		buttonMinus.setBounds(235, 350, 65, 50); //下から３段目、左から４番目に配置。

		button7.setBounds(10, 295, 65, 50); //下から４段目、左から１番目に配置。
		button8.setBounds(85, 295, 65, 50);
		button9.setBounds(160, 295, 65, 50);
		buttonMultiply.setBounds(235, 295, 65, 50);//下から４段目、左から４番目に配置。

		buttonClearentry.setBounds(10, 240, 65, 50);//下から５段目、左から１番目に配置。
		buttonClear.setBounds(85, 240, 65, 50);
		buttonBack.setBounds(160, 240, 65, 50);
		buttonDivide.setBounds(235, 240, 65, 50); //下から５段目、左から４番目に配置。

		buttonPercent.setBounds(10, 185, 65, 50); //下から６段目、左から１番目に配置。
		buttonRoot.setBounds(85, 185, 65, 50);
		buttonJijyo.setBounds(160, 185, 65, 50);
		buttonSomeshingPer1.setBounds(235, 185, 65, 50); //下から６段目、左から４番目に配置。

		/****************************************
		 *アクションイベントを受け取れる様に設定*
		 ****************************************/
		button0.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		buttonReverse.addActionListener(this);
		buttonEqual.addActionListener(this);
		buttonPlus.addActionListener(this);
		buttonMinus.addActionListener(this);
		buttonDot.addActionListener(this);
		buttonMultiply.addActionListener(this);
		buttonDivide.addActionListener(this);
		buttonClear.addActionListener(this);
		buttonClearentry.addActionListener(this);
		buttonBack.addActionListener(this);
		buttonPercent.addActionListener(this);
		buttonRoot.addActionListener(this);
		buttonJijyo.addActionListener(this);
		buttonSomeshingPer1.addActionListener(this);

		/****************************************
		 * 		造ったボタンをパネルに追加 		*
		 ****************************************/
		p.add(buttonReverse); //ボタン「±」
		p.add(button0); //ボタン「０」
		p.add(buttonDot); //ボタン「．」
		p.add(buttonEqual); //ボタン「＝」

		p.add(button1); //ボタン「１」
		p.add(button2); //ボタン「２」
		p.add(button3); //ボタン「３」
		p.add(buttonPlus); //ボタン「＋」

		p.add(button4); //ボタン「４」
		p.add(button5); //ボタン「５」
		p.add(button6); //ボタン「６」
		p.add(buttonMinus); //ボタン「－」

		p.add(button7); //ボタン「７」
		p.add(button8); //ボタン「８」
		p.add(button9); //ボタン「９」
		p.add(buttonMultiply); //ボタン「×」

		p.add(buttonClearentry);//ボタン「CE」
		p.add(buttonClear); //ボタン「Ｃ」
		p.add(buttonBack); //ボタン「Bc」
		p.add(buttonDivide); //ボタン「÷」

		p.add(buttonPercent); //ボタン「％」
		p.add(buttonRoot); //ボタン「√」
		p.add(buttonJijyo); //ボタン「x²」
		p.add(buttonSomeshingPer1); //ボタン「1/x」

		/****************************************
		 * 		ラベルの追加 					*
		 ****************************************/
		label1.setBounds(10, 90, 290, 50);
		label1.setHorizontalAlignment(JLabel.RIGHT);
		label1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 22)); //ラベル1のフォントの変更。
		p.add(label1);

		label2.setBounds(10, 40, 290, 50);
		label2.setHorizontalAlignment(JLabel.RIGHT);
		label2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 12)); //ラベル2のフォントの変更。
		p.add(label2);

		p.addKeyListener((KeyListener) this);//そのままではキー入力を受け付けない為、
		p.setFocusable(true); //パネルでキー入寮を受け付ける処理。

		getContentPane().add(p); //パネルPの追加。

	}

	/************************************************************************
	 *			アクションを受け付けるメソッド。							*
	 *			何のアクションがあったのかを変数eに取り込む、				*
	 *			演算子の条件式を纏められない為、if文ではなくcase文を使用。	*
	 ************************************************************************/
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (errorFlag == true && command != "Ｃ") { //エラー発生中につき、Ｃが押されない限りメソッドを抜ける。
			return;
		}
		switch (command) {
		case "０":
		case "１":
		case "２":
		case "３":
		case "４":
		case "５":
		case "６":
		case "７":
		case "８":
		case "９":
			addNumber(command);
			break;
		case "±":
		case "＝":
		case "＋":
		case "－":
		case "．":
		case "×":
		case "÷":
		case "Ｃ":
		case "CE":
		case "Bc":
		case "％":
		case "√":
		case "x²":
		case "1/x":
			addFunction(command);
			break;
		}
		p.requestFocus();//ボタンに移動してしまったフォーカスをパネルに戻す。
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//未使用。
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//エラー状態の場合キー入力処理を受け付けない様にする為、キー入力メソッドを抜ける。
		if (errorFlag == true) {
			return;
		}
		int key = e.getKeyCode();
		//数字コードで受け取ったキーの種類を判別すし、addNumberメソッドにて数値を加算を行う。
		switch (key) {
		case 96:
			addNumber("０");
			break;
		case 97:
			addNumber("１");
			break;
		case 98:
			addNumber("２");
			break;
		case 99:
			addNumber("３");
			break;
		case 100:
			addNumber("４");
			break;
		case 101:
			addNumber("５");
			break;
		case 102:
			addNumber("６");
			break;
		case 103:
			addNumber("７");
			break;
		case 104:
			addNumber("８");
			break;
		case 105:
			addNumber("９");
			break;
		case 107:
			addFunction("＋");
			break;
		case 109:
			addFunction("－");
			break;
		case 111:
			addFunction("÷");
			break;
		case 106:
			addFunction("×");
			break;
		case 110:
			addFunction("．");
			break;
		case 8:
			addFunction("Bc");
			break;
		case 10:
			addFunction("＝");
			break;
		case 127:
			addFunction("Ｃ");
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 未使用。
	}

	/************************************************************************
	 *			数字が入力された時、一時的数値配列に追加するメソッド。		*
	 ************************************************************************/
	public void addNumber(String singleVal) { //入力された値をtmpVal配列に格納していくメソッド。
		String str;
		if (tmpValCount == 99) { //入力された数字の桁が１００に達した場合、
			label1.setText("Error"); //エラー表示をして処理を抜ける。
			errorFlag = true;
			return;
		}
		if (singleVal == "０" && tmpValCount == 0) { //入力された数字が０で、前回までに入力された値が０である場合、
			label1.setText("0");
			return;
		}
		enzanFlag = false;
		tmpVal[tmpValCount] = singleVal.charAt(0); //値の格納。
		tmpVal[tmpValCount++] -= 65248; //全角数字をキャラクター型の半角数字に変換を行う。値を格納する配列の次の番地を指定する為のカウントアップ。

		showLabel1();//現在入力中の数値の表示を更新。
		str = new String(tmpVal);
		if (val < 0) {
			str = "-" + str;
		}
		label1.setText(str);

	}

	/************************************************************************
	 *			数字以外が入力された時の大体の演算処理を行う。				*
	 *			役目の追加メソッド											*
	 ************************************************************************/
	public void addFunction(String singleStr) { //数字以外の演算子等が入力された場合のメソッド。
		String str;
		double val1, val2;
		byte i;

		switch (singleStr) {
		case "％":
			if (foreVal != 0) {
				val = foreVal * (val / 100);
				label1.setText(longOrDouble());
				while (tmpValCount != 0) {
					tmpVal[--tmpValCount] = 0;
				}
				dotFlag = false;
			}
			break;
		case "．":
			if (dotFlag == true) {
				return;//「.」が２度目押された場合、処理を抜ける。
			}
			if (tmpValCount == 0) { //一桁目から「.」が押された場合。
				tmpVal[tmpValCount++] = '0'; //一桁目に０を格納して、「０．」の状態にする。
				label1.setText("0");
			}
			dotFlag = true;
			enzanFlag = false;
			tmpVal[tmpValCount++] = '.';
			str = label1.getText() + "."; //「.」が押された時に.のみを追加で表記する為の処理。
			label1.setText(str);
			return;
		case "±":
			if (val == 0) {
				return;
			}
			val = -val;
			if (val < 0) {//入力中の値がマイナスなら、
				minusFlag = true;//マイナスフラグを真にする。
			} else {
				minusFlag = false;
			}
			if (tmpVal[0] == 0) {//小計が０ではなく、かつ入力が無い場合。
				label1.setText(longOrDouble());
			} else {
				showLabel1();
			}
			break;
		case "√":
			val = Math.sqrt(val);
			label1.setText(longOrDouble());
			break;
		case "1/x":
			if (val != 0) {
				val = 1 / val;
			}
			label1.setText(longOrDouble());
			break;
		case "x²":
			if (val != 0) {
				val = val * val;
			}
			if (Double.isInfinite(val) == true) {
				label1.setText("Error:Infinity");
				errorFlag = true;
			} else {
				label1.setText(longOrDouble());
			}
			break;
		case "Bc": //Bcかバックスペースキーが押された時、直近の入力された内容を削除する処理。
			if (tmpValCount <= 1) { //入力された数字が一個以下であるなら、
				tmpValCount = 0; //数字が一個入力されていた場合と何も入力されていなかった時の処理とを兼用する。
				tmpVal[tmpValCount] = '0'; //処理が終わったらそのままメソッドを抜ける。
				if (valSequenceCount > 0) {
					enzanFlag = true;

				}
				showLabel1();
				return;
			}

			if (tmpVal[--tmpValCount] == '.') { //削除対象が「.」の場合、
				dotFlag = false; //dotFlagをリセットする。
				if (tmpVal[0] == '0' && valSequenceCount > 0) {
					enzanFlag = true;
					tmpVal[tmpValCount] = 0;
					clearEntry();
					return;
				}
			}
			tmpVal[tmpValCount] = 0; //直近に入力された内容を削除する。
			if (tmpVal[tmpValCount - 1] == '.') { //削除後手前の値が「.」の場合、整数で表示させる処理を行う。
				foreDotFlag = true; //ラベル出力用に手前ドットフラグを立てる。
				showLabel1(); //long型で出力される為、整数での表示となる。
				str = label1.getText() + "."; //その為ラベルの末尾にドットを付け加える。
				label1.setText(str);
				foreDotFlag = false;//ラベルへ出力が終わったら、処理用の手前ドットフラグをリセットする。
			} else { //削除後の手前の値が数字である場合、
				showLabel1(); //そのままラベルに出力を行う。
				str = new String(tmpVal);
				if (val < 0) {
					str = "-" + str;
				}
				label1.setText(str);
			}
			break;
		case "Ｃ":
			allReset();
			showLabel1();
			showLabel2();
			break;
		case "CE": //クリアエントリーが押された場合の処理。入力された数字を削除する。
			clearEntry();
			if (valSequenceCount > 0) {
				enzanFlag = true;
			}
			break;
		case "＋":
		case "－":
		case "×":
		case "÷":
			if (enzanFlag == true) {
				valSequence[valSequenceCount - 1] = singleStr;
				showLabel2();
				return;
			}
			if (val == 0) {//入力された値が０ならばそのまま処理を抜ける。
				return;
			}
			valSequence[valSequenceCount++] = longOrDouble();
			valSequence[valSequenceCount++] = singleStr;//演算子を入れてカウンターをカントアップする。
			enzanFlag = true;
			foreVal = val;
			i = 3;
			subtotal = Double.valueOf(valSequence[0]);
			while (valSequenceCount >= i) {
				if (valSequence[i - 2] == "＋") {
					subtotal += Double.valueOf(valSequence[i - 1]);
				} else if (valSequence[i - 2] == "－") {
					subtotal -= Double.valueOf(valSequence[i - 1]);
				} else if (valSequence[i - 2] == "×") {
					subtotal *= Double.valueOf(valSequence[i - 1]);
				} else if (valSequence[i - 2] == "÷") {
					subtotal /= Double.valueOf(valSequence[i - 1]);
				}
				i += 2;
			}
			showLabel2();
			clearEntry();
			subtotalLong = (long) subtotal;
			if (subtotalLong == subtotal) {
				label1.setText(String.valueOf(subtotalLong));
			} else {
				label1.setText(String.valueOf(subtotal));
			}
			break;
		case "＝":
			if (valSequenceCount < 2 || valSequenceCount == 2 && tmpValCount == 0 && val == 0) {
				return;
			}
			if (enzanFlag == true) {
				valSequence[--valSequenceCount] = null;
				enzanFlag = false;
			} else if (val == 0) {//直前の入力が演算子ではなく、入力数値が０の時に「＝」処理を抜ける。
				return;
			}
			if (val != 0) {
				valSequence[valSequenceCount++] = longOrDouble();
				clearEntry();
			}
			i = 1;
			while (i < valSequenceCount) {
				val1 = Double.valueOf(valSequence[i - 1]);//探し出した演算子の一個手前と、
				val2 = Double.valueOf(valSequence[i + 1]);//一個後ろの数値を取り出し、
				if (valSequence[i] == "×") {//演算子×の場合。
					substituteMixValue((val1 * val2), i);
				} else if (valSequence[i] == "÷") {//÷の場合。
					substituteMixValue((val1 / val2), i);
				} else if (valSequence[i] == "＋") {//演算子＋の場合。
					substituteMixValue((val1 + val2), i);
				} else if (valSequence[i] == "－") {//ーの場合。
					substituteMixValue((val1 - val2), i);
				}
			}
			val = Double.valueOf(valSequence[0]);
			valLong = (long) val;
			if (val == valLong) {//小数か整数かの判定を行い、当てはまる型での表示を行う。
				label1.setText(String.valueOf(valLong));
			} else {
				label1.setText(String.valueOf(val));
			}
			valSequence[0] = null;
			valSequenceCount = 0;
			label2.setText("");
			foreVal = 0;
			if (Double.isInfinite(val) == true) {
				label1.setText("Error:Infinity");
				errorFlag = true;
			}
		}
	}

	/************************************************************************
	 *			演算後の代入と代入後の空白を詰める処理のメソッド。			*
	 *			仮引数には演算結果が入る。									*
	 ************************************************************************/

	void substituteMixValue(double valMix, byte i) {
		byte j;
		valSequence[i - 1] = Double.toString(valMix);
		valSequence[i] = null;
		valSequence[i + 1] = null;
		j = i;
		valSequenceCount -= 2;
		while (valSequence[j + 2] != null) {
			valSequence[j] = valSequence[j + 2];
			valSequence[j + 2] = null;
			j++;
		}
	}

	/************************************************************************
	 *			小数か整数かを判定するメソッド。							*
	 *			例：整数であっても、double型では「123.0」と表示されて		*
	 *			しまう為に、それを避ける方法としてlong型にキャストする事で	*
	 *			「123」と小数点以下の０を表示させない様にする。				*
	 ************************************************************************/
	private String longOrDouble() {
		valLong = (long) val;
		if (valLong == val)
			return String.valueOf(valLong);
		return String.valueOf(val);
	}

	/************************************************************************
	 *			CEを押された時の動作メソッド。								*
	 *			一時入力の配列tmpValのリセットとその他関係する				*
	 *			フラグのリセットを行う。									*
	 ************************************************************************/
	void clearEntry() {
		while (tmpValCount != 0) {
			tmpVal[--tmpValCount] = 0;
		}
		dotFlag = false;
		minusFlag = false;
		val = 0;
		label1.setText("0");
	}

	/************************************************************************
	 *			初期化メソッド。											*
	 *			Ｃボタンを押された場合に行われる処理。						*
	 ************************************************************************/
	void allReset() {
		while (tmpValCount != 0) {//配列tmpValと、カウンターtmpValCountのリセット。
			tmpVal[--tmpValCount] = 0;
		}
		while (valSequenceCount != 0) {//配列valSequenceと、カウンターvalSequenceCountのリセット。
			valSequence[--valSequenceCount] = null;
		}
		errorFlag = false;
		enzanFlag = false;
		dotFlag = false;
		minusFlag = false;
		foreDotFlag = false;
		enzanFlag = false;
		tmpValCount = 0;
		val = 0;
		valSequenceCount = 0;
		subtotalLong = 0;
		subtotal = 0;
		foreVal = 0;
		tmpVal[0] = '0';
	}

	/************************************************************************
	 *			現在入力または合計をラベル１に表示させるメソッド			*
	 *			tmpValに格納された値を一つずつvalに取り出す、				*
	 *			valの値を左にシフトさせる、小数点が出た場合					*
	 *			小数点以下に値を追加していく。								*
	 ************************************************************************/
	void showLabel1() {
		String str;
		val = 0;
		valLong = 0;
		str = new String(tmpVal);
		if (Double.valueOf(str) == 0) {//入力値が空っぽの場合、
			str = "0";//ラベル１に０を出力する為に文字列０を代入する。
			minusFlag = false;//バックスペース等で０になった場合を想定して、マイナスフラグを折る。
		}
		val = Double.valueOf(str);
		if (minusFlag == true) {//「±」を押した時の数字の反転を行う。
			val = -val;
		}
		valLong = (long) val;
		if (valLong == val) {
			label1.setText(String.valueOf(valLong));
		} else {
			if (minusFlag == true) {
				str = "-" + str;
			}
			label1.setText(str);
		}
	}

	/************************************************************************
	 *			これまでに入力された数値と演算子をlabel2に表示する。		*
	 ************************************************************************/
	void showLabel2() {
		byte i;
		String str = "";
		for (i = 0; i < valSequenceCount; i++) {
			str = str + valSequence[i];
		}
		label2.setText(str);
	}

}
