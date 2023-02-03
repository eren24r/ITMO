class lamda{
public static int StrOp(StringF f, int n){
return f.fun(n);
}
public static void main(String[] arg){
StringF i = (n) -> n*n;
int result = i.fun(5);
System.out.println(result);
}
}
interface StringF{
int fun(int n);
}
