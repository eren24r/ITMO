###init 
svnadmin create C:\Users\eren\Documents\lab2\lab2\lab2_opi
svn mkdir file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk -m "Create trunk directory"
svn mkdir file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches -m "Create branches directory"
svn mkdir file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/tags -m "Create tags directory"

###rep
svn checkout file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk lab2_opi
cd lab2_opi

###r0
mkdir src
cp -r ..\..\..\commits\commit0\* .\src\
svn add *
svn commit -m "r0" --username red

###blueBranch
svn copy file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/bluebr -m "Create branch bluebr"
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/bluebr

###r1
cp -r ..\..\..\commits\commit1\* .\src\
svn add --force *
svn commit -m "r1" --username blue

###r2
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk
cp -r ..\..\..\commits\commit2\* .\src\
svn add --force * 
svn commit -m "r2" --username red

###redBranch
svn copy file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/redbr -m "Create branch redbr"
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/redbr

###r3
cp -r ..\..\..\commits\commit3\* .\src\
svn add --force * 
svn commit -m "r3" --username red

###r4
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/bluebr
cp -r ..\..\..\commits\commit4\* .\src\
svn add --force *
svn commit -m "r4" --username blue

###r5
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/redbr
cp -r ..\..\..\commits\commit5\* .\src\
svn add --force *
svn commit -m "r5" --username red

###r6
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/bluebr
cp -r ..\..\..\commits\commit6\* .\src\
svn add --force *
svn commit -m "r6" --username blue

###r7
cp -r ..\..\..\commits\commit7\* .\src\
svn add --force *
svn commit -m "r7" --username blue

###r8
cp -r ..\..\..\commits\commit8\* .\src\
svn add --force *
svn commit -m "r8" --username red

###r9
cp -r ..\..\..\commits\commit9\* .\src\
svn add --force *
svn commit -m "r9" --username red

###r10
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk
svn resolve --accept working C:\Users\eren\Documents\lab2\lab2\lab2_opi\lab2_opi\src\*
svn merge file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/redbr

cp -r ..\..\..\commits\commit10\* .\src\
svn add --force *
svn commit -m "r10" --username red

###r11
cp -r ..\..\..\commits\commit11\* .\src\
svn add --force *
svn commit -m "r11" --username red

###r12
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/bluebr
cp -r ..\..\..\commits\commit12\* .\src\
svn add --force *
svn commit -m "r12" --username blue 

###r13
cp -r ..\..\..\commits\commit13\* .\src\
svn add --force *
svn commit -m "r13" --username blue

###r14
svn switch file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/trunk
svn merge file:///C:/Users/eren/Documents/lab2/lab2/lab2_opi/branches/bluebr

cp -r ..\..\..\commits\commit14\* .\src\
svn add --force *
svn resolve --accept working C:\Users\eren\Documents\lab2\lab2\lab2_opi\lab2_opi\src\_
svn resolve --accept working C:\Users\eren\Documents\lab2\lab2\lab2_opi\lab2_opi\src\Lab4.java
svn commit -m "r14" --username red
