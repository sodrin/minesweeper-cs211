/**
 * Example of using unit tests for this assignment.
 * To run them on the command line, make sure that
 * the junit-cs211.jar is in the same directory.
 * <p>
 * On Mac/Linux:
 * javac -cp .:junit-cs211.jar *.java         # compile everything
 * java -cp .:junit-cs211.jar P2Tester        # run tests
 * <p>
 * On windows replace colons with semicolons: (: with ;)
 * demo$ javac -cp .;junit-cs211.jar *.java   # compile everything
 * demo$ java -cp .;junit-cs211.jar P2Tester  # run tests
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;

public class P2Tester {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("P2Tester");
    }

    //// helper functions
    // check whether class exists
    private boolean isClassFound(String name) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException cnfe) {

        }
        return c != null;
    }

    // assert class found
    private void assertClassFound(String name) {
        assertTrue(name + " class not found", isClassFound(name));
    }

    private void assertClassExists(String name)
    {
        try
        {
            Class<?> c = Class.forName(name);
        }
        catch (ClassNotFoundException cnfe)
        {
            fail("class " + name + " not found");
        }
    }

    // get class when existing
    private Class<?> getClassWhenExisting(String name) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException cnfe) {

        }
        return c;
    }

    // check constructor
    private void checkConstructor(Class<?> cls, Class<?>... parameterTypes) {
        Constructor<?> c = null;
        try {
            c = cls.getConstructor(parameterTypes);
        } catch (NoSuchMethodException nsme) {
        }
        assertTrue(cls.getName() + " constructor with correct parameters not found", c != null);
    }

    // check method
    private void checkMethod(String className, String methodName, boolean checkPublic, boolean checkStatic, Class<?> returnType, Class<?>... parameterTypes) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);

        Method m = null;
        try {
            Class[] cArg = (Class[]) parameterTypes;
            m = c.getDeclaredMethod(methodName, cArg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assertTrue(methodName + " method not found", m != null);
        assertTrue(methodName + " return type is incorrect", m.getReturnType() == returnType);
        if (checkStatic)
            assertTrue(methodName + " is not static", Modifier.isStatic(m.getModifiers()));
        if (checkPublic)
            assertTrue(methodName + " is not public", Modifier.isPublic(m.getModifiers()));
    }

    // check method when existing
    private void checkMethodWhenExisting(String className, String methodName, boolean checkPublic, boolean checkStatic, Class<?> returnType, Class<?>... parameterTypes) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);
        Method m = null;
        try {
            Class[] cArg = (Class[]) parameterTypes;
            m = c.getDeclaredMethod(methodName, cArg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (m != null) {
            // only check when existing
            assertTrue(methodName + " return type is incorrect", m.getReturnType() == returnType);
            if (checkStatic)
                assertTrue(methodName + " is not static", Modifier.isStatic(m.getModifiers()));
            if (checkPublic)
                assertTrue(methodName + " is not public", Modifier.isPublic(m.getModifiers()));
        }
    }

    // check class's fields
    private void checkClassField(String className, String field, Class<?> type, boolean checkPublic, boolean checkStatic) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);

        Field f = null;
        try {
            f = c.getField(field);
        } catch (NoSuchFieldException e) {
        }
        assertTrue("Field " + field + " not found", f != null);
        assertTrue("Field " + field + " should be " + type.getName(), f.getType() == type);
        if (checkPublic)
            assertTrue("Field " + field + " should be public", Modifier.isPublic(f.getModifiers()));
        if (checkStatic)
            assertTrue("Field " + field + " should be static", Modifier.isStatic(f.getModifiers()));
    }

    // get field
    private Field getFieldWhenExisting(String className, String field) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);

        Field f = null;
        try {
            f = c.getDeclaredField(field);
            f.setAccessible(true); // required for private field
        } catch (NoSuchFieldException e) {
        }
        assertTrue("Field " + field + " not found in class " + className, f != null);
        return f;
    }

    // get method
    private boolean isMethodExisting(String className, String methodName, Class<?>... parameterTypes) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);
        Method m = null;
        try {
            Class[] cArg = (Class[]) parameterTypes;
            m = c.getDeclaredMethod(methodName, cArg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return m != null;
    }

    // does array contain element
    private boolean contains(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element))
                return true;
        }
        return false;
    }

    private boolean methodsEqual(Method[] methods, String[] names) {
        for (int i = 0; i < methods.length; i++) {
            if (!contains(names, methods[i].getName())) return false;
        }
        return true;
    }

    // get all methods
    private Method[] getAllMethods(Class<?> c, boolean includePublic, boolean includeProtected, boolean includePrivate, boolean isStatic) {
        Method[] ms = null;
        try {
            ms = c.getDeclaredMethods();
        } catch (SecurityException se) {

        }
        if (ms == null) return ms;
        ArrayList<Method> result = new ArrayList<Method>();
        boolean ispub = false;
        boolean ispro = false;
        boolean ispri = false;
        boolean issta = false;
        for (Method m : ms) {
            ispub = Modifier.isPublic(m.getModifiers());
            ispro = Modifier.isProtected(m.getModifiers());
            ispri = Modifier.isPrivate(m.getModifiers());
            issta = Modifier.isStatic(m.getModifiers());
            if (isStatic == issta) {
                if (includePublic && ispub) result.add(m);
                else if (includeProtected && ispro) result.add(m);
                else if (includePrivate && ispri) result.add(m);
            }
        }
        return result.toArray(new Method[]{});
    }

    // get all fields
    private Field[] getAllFields(Class<?> c, boolean includePublic, boolean includeProtected, boolean includePrivate, boolean splitStatic, boolean isStatic) {
        Field[] fs = null;
        try {
            //get only the fields declared in the class, exclude inherited fields
            fs = c.getDeclaredFields();
        } catch (SecurityException se) {

        }
        if (fs == null) return fs;
        ArrayList<Field> result = new ArrayList<Field>();
        boolean ispub = false;
        boolean ispro = false;
        boolean ispri = false;
        boolean issta = false;
        for (Field f : fs) {
            ispub = Modifier.isPublic(f.getModifiers());
            ispro = Modifier.isProtected(f.getModifiers());
            ispri = Modifier.isPrivate(f.getModifiers());
            issta = Modifier.isStatic(f.getModifiers());
            if (splitStatic) {
                if (isStatic == issta) {
                    if (includePublic && ispub) result.add(f);
                    else if (includeProtected && ispro) result.add(f);
                    else if (includePrivate && ispri) result.add(f);
                }
            } else {
                if (includePublic && ispub) result.add(f);
                else if (includeProtected && ispro) result.add(f);
                else if (includePrivate && ispri) result.add(f);
            }
        }
        return result.toArray(new Field[]{});
    }

    // check class's all methods
    private void checkAllPublicMethods(String className, String[] methods, boolean checkStatic) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);

        Method[] ms = getAllMethods(c, true, false, false, checkStatic);
        assertTrue("Public methods count check failed.", ms != null && ms.length == methods.length);
        assertTrue("Public methods names check failed", methodsEqual(ms, methods));
    }

    // check no public fields
    private void checkNoPublicFields(String className, boolean splitStatic, boolean isStatic) {
        assertClassFound(className);
        Class<?> c = getClassWhenExisting(className);

        Field[] fs = getAllFields(c, true, false, false, splitStatic, isStatic);
        assertTrue("Class " + className + " shouldn't have public fields", fs.length == 0);
    }

    // check private field through reflection
    private void checkPrivateField(String className, String fieldName, Object expectedValue, Class<?>[] parameterTypes, Object... parameters) {
        assertClassFound(className);
        Class<?> cls = getClassWhenExisting(className);

        Object obj = null;
        try {
            Constructor constructor = cls.getConstructor(parameterTypes);
            obj = (Object) constructor.newInstance(parameters);
        } catch (NoSuchMethodException nsme) {

        } catch (InvocationTargetException e) {

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        assertTrue("Calling constructor failed", obj != null);

        Field f = getFieldWhenExisting(className, fieldName);
        Object value = null;
        boolean accessFailed = false;
        try {
            value = (Object) f.get(obj);
        } catch (IllegalAccessException iae) {
            accessFailed = true;
        }
        assertTrue("Accessing private field " + fieldName + " failed", !accessFailed);
        assertTrue("Private field " + fieldName + " check failed", value.equals(expectedValue));
    }

    // private check private field not null
    private void checkPrivateFieldNotNull(String className, String fieldName, Class<?>[] parameterTypes, Object... parameters) {
        assertClassFound(className);
        Class<?> cls = getClassWhenExisting(className);

        Object obj = null;
        try {
            Constructor constructor = cls.getConstructor(parameterTypes);
            obj = (Object) constructor.newInstance(parameters);
        } catch (NoSuchMethodException nsme) {

        } catch (InvocationTargetException e) {

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        assertTrue("Calling constructor failed", obj != null);

        Field f = getFieldWhenExisting(className, fieldName);
        Object value = null;
        boolean accessFailed = false;
        try {
            value = (Object) f.get(obj);
        } catch (IllegalAccessException iae) {
            accessFailed = true;
        }
        assertTrue("Accessing private field " + fieldName + " failed", !accessFailed);
        assertTrue("Private field " + fieldName + " null check failed", value != null);
    }

	// reset all the configuration parameters to prevent the use of
	// previously cached values
	private void resetConfigurationParameters()
	{
		Configuration.ROWS = -1;
		Configuration.COLS = -1;
		Configuration.MINES = -1;
		Configuration.CELL_SIZE = -1;
		Configuration.BOARD_WIDTH = -1;
		Configuration.BOARD_HEIGHT = -1;
		Configuration.STATUS_COVERED = "";
		Configuration.STATUS_OPENED = "";
		Configuration.STATUS_MARKED = "";
		Configuration.STATUS_WRONGLY_MARKED = "";
	}

    // Configuration
    @Test(timeout = 1000)
    public void test_Configuration_00() {
        assertClassExists("Configuration");
    }

    @Test(timeout = 1000)
    public void test_Configuration_01() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "ROWS", int.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_02() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "COLS", int.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_03() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "CELL_SIZE", int.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_04() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "MINES", int.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_05() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "BOARD_WIDTH", int.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_06() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "BOARD_HEIGHT", int.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_07() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "STATUS_COVERED", String.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_08() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "STATUS_OPENED", String.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_09() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "STATUS_MARKED", String.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_10() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "STATUS_WRONGLY_MARKED", String.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_11() {
        String name = "Configuration";
        assertClassFound(name);
        checkClassField(name, "STATUS_WRONGLY_MARKED", String.class, true, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_12() {
        String name = "Configuration";
        assertClassFound(name);
        checkMethod(name, "loadParameters", true, true, void.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_Configuration_13() {
        String name = "Configuration";
        assertClassFound(name);
        checkAllPublicMethods(name, new String[]{"loadParameters"}, true);
    }

    @Test(timeout = 1000)
    public void test_Configuration_14() {
		resetConfigurationParameters();
        assertClassFound("Configuration");
        Configuration.loadParameters("config1.test");
        assertEquals("Configuration ROWS check failed", 20, Configuration.ROWS);
        assertEquals("Configuration COLS check failed", 20, Configuration.COLS);
        assertEquals("Configuration MINES check failed", 50, Configuration.MINES);
        assertEquals("Configuration CELL_SIZE check failed", 15, Configuration.CELL_SIZE);
        assertEquals("Configuration STATUS_COVERED check failed", "cover", Configuration.STATUS_COVERED);
        assertEquals("Configuration STATUS_OPENED check failed", "open", Configuration.STATUS_OPENED);
        assertEquals("Configuration STATUS_MARKED check failed", "mark", Configuration.STATUS_MARKED);
        assertEquals("Configuration STATUS_WRONGLY_MARKED check failed", "wrongmark", Configuration.STATUS_WRONGLY_MARKED);
        assertEquals("Configuration BOARD_WIDTH check failed", 301, Configuration.BOARD_WIDTH);
        assertEquals("Configuration BOARD_HEIGHT check failed", 301, Configuration.BOARD_HEIGHT);
    }

    @Test(timeout = 1000)
    public void test_Configuration_15() {
		resetConfigurationParameters();
        assertClassFound("Configuration");
        Configuration.loadParameters("config2.test");
        assertEquals("Configuration ROWS check failed", 38, Configuration.ROWS);
        assertEquals("Configuration COLS check failed", 17, Configuration.COLS);
        assertEquals("Configuration MINES check failed", 7, Configuration.MINES);
        assertEquals("Configuration CELL_SIZE check failed", 15, Configuration.CELL_SIZE);
        assertEquals("Configuration STATUS_COVERED check failed", "covered", Configuration.STATUS_COVERED);
        assertEquals("Configuration STATUS_OPENED check failed", "opened", Configuration.STATUS_OPENED);
        assertEquals("Configuration STATUS_MARKED check failed", "marked", Configuration.STATUS_MARKED);
        assertEquals("Configuration STATUS_WRONGLY_MARKED check failed", "wrongly_marked", Configuration.STATUS_WRONGLY_MARKED);
        assertEquals("Configuration BOARD_WIDTH check failed", 256, Configuration.BOARD_WIDTH);
        assertEquals("Configuration BOARD_HEIGHT check failed", 571, Configuration.BOARD_HEIGHT);
    }

    private void check_Minefield_5() {
        //        5. logic test: call the constructor and then count the number of MineCell objects to assert the number is correct
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        final int size = 10;
        Minefield mf = new Minefield(size, size, size);
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mf.getCellByRowCol(i, j) instanceof MineCell) count += 1;
            }
        }
        assertTrue("the number of MineCell objects check failed", count == size);
    }

    private void check_Minefield_6() {
        //        6. logic test: call the constructor and then count the number of InfoCell objects to assert the number is correct
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        final int size = 10;
        Minefield mf = new Minefield(size, size, size);
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mf.getCellByRowCol(i, j) instanceof InfoCell) count += 1;
            }
        }
        assertTrue("the number of InfoCell objects check failed", count == size * size - size);
    }


    private void check_Minefield_11() {
        //        11. logic test: call constructor and then assert getCellByRowCol
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");
        final int row = 7;
        final int col = 7;
        final int mines = 24;
        Minefield mf = new Minefield(row, col, mines);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // get cell
                Object cell = mf.getCellByRowCol(i, j);
                assertTrue(String.format("Check Minefield at (%d,%d) failed", i, j), (cell instanceof MineCell) || (cell instanceof InfoCell));
            }
        }
    }

    private void check_Minefield_12() {
        //        12. logic test: call loadParameters, then the constructor, and then assert getCellByScreenCoordinates
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");
        assertClassFound("Configuration");
        Configuration.loadParameters("config1.test");
        final int row = Configuration.ROWS;
        final int col = Configuration.COLS;
        final int mines = Configuration.MINES;
        Minefield mf = new Minefield(row, col, mines);
        for (int i = 0; i < Configuration.BOARD_WIDTH - 1; i++) {
            for (int j = 0; j < Configuration.BOARD_HEIGHT - 1; j++) {
                // get cell
                Object cell = mf.getCellByScreenCoordinates(i, j);
                assertTrue(String.format("Check Minefield at (%d,%d) failed", i, j), (cell instanceof MineCell) || (cell instanceof InfoCell));
            }
        }

    }

    private void check_Minefield_13() {
        //        13. logic test: call setInfoCell and then assert by calling getCellByRowCol
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        final int row = 7;
        final int col = 7;
        final int mines = 24;
        Minefield mf = new Minefield(row, col, mines);
        InfoCell ic = new InfoCell(0, 0, 0);
        mf.setInfoCell(0, 0, ic);
        assertTrue("setInfoCell/getCellByRowCol check failed", ic.equals(mf.getCellByRowCol(0, 0)));

    }

    private void check_Minefield_14() {
        //        14. logic test: call setMineCell and then assert by calling getCellByRowCol
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        final int row = 7;
        final int col = 7;
        final int mines = 24;
        Minefield mf = new Minefield(row, col, mines);
        MineCell mc = new MineCell(0, 0);
        mf.setMineCell(0, 0, mc);
        assertTrue("setMineCell/getCellByRowCol check failed", mc.equals(mf.getCellByRowCol(0, 0)));
    }

    private void check_Minefield_15() {
        //        15. logic test: call constructor, then call setStatus to change the status of various cells, and then assert correctness of countCellsWithStatus
        assertClassFound("Minefield");
        assertClassFound("MineCell");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");
        final int row = 7;
        final int col = 7;
        final int mines = 24;
        Minefield mf = new Minefield(row, col, mines);
        // reset minefield
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // fill with null
                mf.setInfoCell(i, j, null);
            }
        }
        // set info cell in the diagonal
        for (int i = 0; i < row; i++)
            mf.setInfoCell(i, i, new InfoCell(i, i, 0));

        int count = mf.countCellsWithStatus("cover"); // 7
        assertTrue("countCellsWithStatus check failed", count == row);
        // set status
        for (int i = 0; i < row; i++) {
            InfoCell ic = (InfoCell) mf.getCellByRowCol(i, i);
            ic.setStatus("open");
        }
        count = mf.countCellsWithStatus("open"); // 7
        assertTrue("setStatus/countCellsWithStatus check failed", count == row);
    }

    private Minefield make_MineField_5x5_5mines_in_diagonal() {
        // in 5x5 board, 5 mines in the diagonal
        // ensure classes exist
        assertClassFound("Board");
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        assertClassFound("MineCell");
        assertClassFound("Minefield");
        // init Configuration
        final int size = 5;
        Configuration.ROWS = size;
        Configuration.COLS = size;
        Configuration.CELL_SIZE = size;
        Configuration.MINES = size;
        Configuration.BOARD_HEIGHT = size * size + 1;
        Configuration.BOARD_WIDTH = size * size + 1;
        Configuration.STATUS_COVERED = "cover";
        Configuration.STATUS_MARKED = "mark";
        Configuration.STATUS_OPENED = "open";
        Configuration.STATUS_WRONGLY_MARKED = "wrongmark";
        // change minefield, set mines in the diagonal,
        Minefield minefield = new Minefield(Configuration.ROWS, Configuration.COLS, Configuration.MINES);
        // reset minefield
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // fill with null
                minefield.setMineCell(i, j, null);
            }
            // set mine in the diagonal
            minefield.setMineCell(i, i, new MineCell(i, i));
        }
        // call method to deploy infocells
        minefield.addInfoCells();
        return minefield;
    }

    private void check_Minefield_16() {
        //        16. logic test: create a small minefield (e.g. 5x4) and hard-code the cells. Then change the status of an InfoCell to "uncovered" and call openCells(). Then check all the uncovered cells one by one to assert the correctness of openCells()

        Minefield minefield = make_MineField_5x5_5mines_in_diagonal();
        // [0, 3] is an infocell, if clicked, open its 8 neighbors:[0,2],[0,4],[1,2],[1,3],[1,4]
        int row = 0;
        int col = 3;
        InfoCell cell = (InfoCell) minefield.getCellByRowCol(row, col);
        cell.setStatus(Configuration.STATUS_OPENED);
        minefield.openCells((Object) cell);
        // check status
        final int[][] opened = {
                {0, 2},
                {0, 3},
                {0, 4},
                {1, 2},
                {1, 3},
                {1, 4}
        };
        boolean pass = true;
        for (int i = 0; i < opened.length; i++)
            if (!((InfoCell) minefield.getCellByRowCol(opened[i][0], opened[i][1])).getStatus().equals(Configuration.STATUS_OPENED)) {
                pass = false;
                break;
            }
        assertTrue("openCells check failed", pass);
    }

    private void check_Minefield_17() {
        //        17. logic test: create a small minefield (e.g. 5x4) and hard-code the cells. Then change the status of some InfoCells to "marked" and call revealIncorrectMarkedCells(). Then check the wrongly marked cells to assert that their status was changed to wrongly_marked
        // left button on minecell -- STATUS_OPENED, gameover
        // [1,1] is a mine cell

        // right button on info cell, test wrong mark
        // [2,1] is an infocell
        Minefield minefield = make_MineField_5x5_5mines_in_diagonal();
        InfoCell infoCell = (InfoCell) minefield.getCellByRowCol(2, 1);
        infoCell.setStatus(Configuration.STATUS_MARKED);
        // infocell status should be marked(actually wrongly mark)
        minefield.revealIncorrectMarkedCells();

        int number = minefield.countCellsWithStatus(Configuration.STATUS_WRONGLY_MARKED); // 1
        assertTrue("revealIncorrectMarkedCells check failed", number == 1);
    }

    @Test(timeout = 1000)
    public void test_Minefield_01() {
        String name = "Minefield";
        assertClassFound(name);

        checkConstructor(getClassWhenExisting(name));
        checkConstructor(getClassWhenExisting(name), int.class, int.class, int.class);
    }


    @Test(timeout = 1000)
    public void test_Minefield_02() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkNoPublicFields(name, false, false);
    }


    @Test(timeout = 1000)
    public void test_Minefield_03() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        if (isMethodExisting(name, "mineLaying", int.class, String.class)) {
            // 2 mineLaying for honor section
            checkAllPublicMethods(name, new String[]{"mineLaying", "mineLaying", "addInfoCells", "draw",
                    "getCellByScreenCoordinates", "getCellByRowCol", "setMineCell", "setInfoCell", "countCellsWithStatus",
                    "openCells", "revealIncorrectMarkedCells"}, false);
        } else {
            checkAllPublicMethods(name, new String[]{"mineLaying", "addInfoCells", "draw",
                    "getCellByScreenCoordinates", "getCellByRowCol", "setMineCell", "setInfoCell", "countCellsWithStatus",
                    "openCells", "revealIncorrectMarkedCells"}, false);
        }
    }

    @Test(timeout = 1000)
    public void test_Minefield_05() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_5();
    }

    @Test(timeout = 1000)
    public void test_Minefield_06() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_6();
    }


    @Test(timeout = 1000)
    public void test_Minefield_08() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "mineLaying", true, false, void.class, int.class);
        // this is for honors section
        checkMethodWhenExisting(name, "mineLaying", true, false, void.class, int.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_09() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "addInfoCells", true, false, void.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_10() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "draw", true, false, void.class, Graphics.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_11() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "getCellByScreenCoordinates", true, false, Object.class, int.class, int.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_12() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "getCellByRowCol", true, false, Object.class, int.class, int.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_13() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "setMineCell", true, false, void.class, int.class, int.class, getClassWhenExisting("MineCell"));
    }

    @Test(timeout = 1000)
    public void test_Minefield_14() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "setInfoCell", true, false, void.class, int.class, int.class, getClassWhenExisting("InfoCell"));

    }

    @Test(timeout = 1000)
    public void test_Minefield_15() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "countCellsWithStatus", true, false, int.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_16() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        checkMethod(name, "openCells", true, false, void.class, Object.class);
    }

    @Test(timeout = 1000)
    public void test_Minefield_17() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_11();
    }

    @Test(timeout = 1000)
    public void test_Minefield_18() {

        check_Minefield_12();
    }

    @Test(timeout = 1000)
    public void test_Minefield_19() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_13();
    }

    @Test(timeout = 1000)
    public void test_Minefield_20() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_14();
    }

    @Test(timeout = 1000)
    public void test_Minefield_21() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_15();
    }

    @Test(timeout = 1000)
    public void test_Minefield_22() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_16();
    }

    @Test(timeout = 1000)
    public void test_Minefield_23() {
        String name = "Minefield";
        assertClassFound(name);

        assertClassFound("MineCell");
        assertClassFound("InfoCell");

        check_Minefield_17();
    }


    // MineCell
    private void check_MineCell_7() {
        // 7. logic tests: call the loadParameters() and the MineCell constructor and then check the return value of getHorizontalPosition(). Repeat for several MineCell objects
        assertClassFound("Configuration");
        assertClassFound("MineCell");
        Configuration.loadParameters("config1.test");
        int size = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                MineCell mc = new MineCell(i, j);
                assertTrue("getHorizontalPosition check failed", mc.getHorizontalPosition() == j * 15);
            }
        }
    }

    private void check_MineCell_12() {
        // 12. logic tests: call the loadParameters() and the MineCell constructor and then check the return value of getVerticalPosition(). Repeat for several MineCell objects
        assertClassFound("Configuration");
        assertClassFound("MineCell");
        Configuration.loadParameters("config1.test");
        int size = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                MineCell mc = new MineCell(i, j);
                assertTrue("getHorizontalPosition check failed", mc.getVerticalPosition() == i * 15);
            }
        }
    }

    private void check_MineCell_21() {
        // 21. logic tests: call the loadParameters() and the MineCell constructor and then assert the getStatus value is correct. Do NOT use the variable Configuration.STATUS_COVERED but the String literal we have used in the config file.
        assertClassFound("Configuration");
        assertClassFound("MineCell");
        Configuration.loadParameters("config1.test");

        MineCell mc = new MineCell(1, 1);
        assertTrue("getStatus check failed", mc.getStatus().equals("cover"));
    }

    private void check_MineCell_22() {
        // 22. logic tests: call the setStatus and then the getStatus to assert they're correct. Repeat a few times.
        assertClassFound("Configuration");
        assertClassFound("MineCell");
        Configuration.loadParameters("config1.test");

        final String[] status = new String[]{"cover", "open", "mark"};
        for (int i = 0; i < status.length; i++) {
            MineCell mc = new MineCell(i, i);
            mc.setStatus(status[i]);
            assertTrue("setStatus check failed", mc.getStatus().equals(status[i]));
        }
    }

    private void check_MineCell_27() {
        // 27. logic tests: call the setStatus and then the getImage to assert it's correct. 3 separate tests, one for each status.
        assertClassFound("Configuration");
        assertClassFound("MineCell");
        Configuration.loadParameters("config1.test");

        final String[] status = new String[]{"cover", "open", "mark"};
        final String[] imgName = new String[]{"img/covered_cell.png", "img/mine_cell.png", "img/marked_cell.png"};
        MineCell mc = new MineCell(0, 0);
        for (int i = 0; i < status.length; i++) {
            mc.setStatus(status[i]);
            // get image
            assertTrue("getImage check failed when status is " + status[i], mc.getImage().equals(new ImageIcon(imgName[i]).getImage()));
        }
    }

    @Test(timeout = 1000)
    public void test_MineCell_01() {
        String name = "MineCell";
        assertClassFound(name);

        checkConstructor(getClassWhenExisting(name), int.class, int.class);
    }

    @Test(timeout = 1000)
    public void test_MineCell_03() {
        String name = "MineCell";
        assertClassFound(name);

        checkMethod(name, "getHorizontalPosition", true, false, int.class);
    }

    @Test(timeout = 1000)
    public void test_MineCell_04() {
        String name = "MineCell";
        assertClassFound(name);

        check_MineCell_7();
    }

    @Test(timeout = 1000)
    public void test_MineCell_05() {
        String name = "MineCell";
        assertClassFound(name);

        checkMethod(name, "getVerticalPosition", true, false, int.class);
    }

    @Test(timeout = 1000)
    public void test_MineCell_06() {
        String name = "MineCell";
        assertClassFound(name);

        check_MineCell_12();
    }

    @Test(timeout = 1000)
    public void test_MineCell_07() {
        String name = "MineCell";
        assertClassFound(name);

        checkMethod(name, "getStatus", true, false, String.class);
    }

    @Test(timeout = 1000)
    public void test_MineCell_08() {
        String name = "MineCell";
        assertClassFound(name);

        checkMethod(name, "setStatus", true, false, void.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_MineCell_09() {
        String name = "MineCell";
        assertClassFound(name);

        check_MineCell_21();
    }

    @Test(timeout = 1000)
    public void test_MineCell_10() {
        String name = "MineCell";
        assertClassFound(name);

        check_MineCell_22();
    }

    @Test(timeout = 1000)
    public void test_MineCell_11() {
        String name = "MineCell";
        assertClassFound(name);

        checkMethod(name, "getImage", true, false, Image.class);
    }

    @Test(timeout = 1000)
    public void test_MineCell_12() {
        String name = "MineCell";
        assertClassFound(name);

        check_MineCell_27();
    }

    @Test(timeout = 1000)
    public void test_MineCell_13() {
        String name = "MineCell";
        assertClassFound(name);

        checkAllPublicMethods(name, new String[]{"getHorizontalPosition", "getVerticalPosition", "getStatus", "setStatus", "getImage", "draw"}, false);
    }

    private void check_InfoCell_7() {
        //        7. logic tests: call the loadParameters() and the InfoCell constructor and then check the return value of getHorizontalPosition(). Repeat for several InfoCell objects
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");
        int size = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                InfoCell ic = new InfoCell(i, j, 0);
                assertTrue("getHorizontalPosition check failed", ic.getHorizontalPosition() == j * 15);
            }
        }
    }

    private void check_InfoCell_12() {
        //        12. logic tests: call the loadParameters() and the InfoCell constructor and then check the return value of getVerticalPosition(). Repeat for several InfoCell objects
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");
        int size = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                InfoCell ic = new InfoCell(i, j, 0);
                assertTrue("getVerticalPosition check failed", ic.getVerticalPosition() == i * 15);
            }
        }
    }

    private void check_InfoCell_21() {
        //        21. logic tests: call the loadParameters() and the InfoCell constructor and then assert the getStatus value is correct. Do NOT use the variable Configuration.STATUS_COVERED but the String literal we have used in the config file.
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");
        int size = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                InfoCell ic = new InfoCell(i, j, 0);
                assertTrue("getStatus check failed", ic.getStatus().equals("cover"));
            }
        }
    }

    private void check_InfoCell_22() {
        //        22. logic tests: call the setStatus and then the getStatus to assert they're correct. Repeat a few times.
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");

        final String[] status = new String[]{"cover", "open", "mark", "wrongmark"};
        for (int i = 0; i < status.length; i++) {
            InfoCell ic = new InfoCell(i, i, 0);
            ic.setStatus(status[i]);
            assertTrue("setStatus check failed", ic.getStatus().equals(status[i]));
        }
    }

    private void check_InfoCell_27() {
        //        27. logic tests: call the setStatus and then the getImage to assert it's correct. 12 separate tests, one for each status.
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");

        final String[] status = new String[]{"cover", "wrongmark", "mark"};
        final String[] imgName = new String[]{"img/covered_cell.png", "img/wrong_mark.png", "img/marked_cell.png"};
        for (int i = 0; i < status.length; i++) {
            InfoCell ic = new InfoCell(i, i, 0);
            ic.setStatus(status[i]);
            // get image
            assertTrue("getImage check failed when status is " + status[i], ic.getImage().equals(new ImageIcon(imgName[i]).getImage()));
        }
        // test open
        for (int i = 0; i < 9; i++) {
            InfoCell ic = new InfoCell(i, i, i);
            ic.setStatus("open");
            String filename = String.format("img/info_%d.png", i);
            // get image
            assertTrue("getImage check failed when status is open(" + i + ").", ic.getImage().equals(new ImageIcon(filename).getImage()));
        }
    }

    private void check_InfoCell_32() {
        //        32. logic tests: call the loadParameters() and the InfoCell constructor and then assert the return value from getNumOfAdjacentMines()
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        Configuration.loadParameters("config1.test");
        InfoCell ic = new InfoCell(10, 10, 10);
        assertTrue("getNumOfAdjacentMines check failed", ic.getNumOfAdjacentMines() == 10);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_01() {
        String name = "InfoCell";
        assertClassFound(name);

        checkConstructor(getClassWhenExisting(name), int.class, int.class, int.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_02() {
        String name = "InfoCell";
        assertClassFound(name);

        checkNoPublicFields(name, false, false);
    }


    @Test(timeout = 1000)
    public void test_InfoCell_04() {
        String name = "InfoCell";
        assertClassFound(name);

        checkMethod(name, "getHorizontalPosition", true, false, int.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_05() {
        String name = "InfoCell";
        assertClassFound(name);

        check_InfoCell_7();
    }

    @Test(timeout = 1000)
    public void test_InfoCell_06() {
        String name = "InfoCell";
        assertClassFound(name);

        checkMethod(name, "getVerticalPosition", true, false, int.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_07() {
        String name = "InfoCell";
        assertClassFound(name);

        check_InfoCell_12();
    }

    @Test(timeout = 1000)
    public void test_InfoCell_08() {
        String name = "InfoCell";
        assertClassFound(name);

        checkMethod(name, "getStatus", true, false, String.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_09() {
        String name = "InfoCell";
        assertClassFound(name);

        checkMethod(name, "setStatus", true, false, void.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_10() {
        String name = "InfoCell";
        assertClassFound(name);

        check_InfoCell_21();
    }

    @Test(timeout = 1000)
    public void test_InfoCell_11() {
        String name = "InfoCell";
        assertClassFound(name);

        check_InfoCell_22();
    }

    @Test(timeout = 1000)
    public void test_InfoCell_12() {
        String name = "InfoCell";
        assertClassFound(name);

        checkMethod(name, "getImage", true, false, Image.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_13() {
        String name = "InfoCell";
        assertClassFound(name);

        check_InfoCell_27();
    }

    @Test(timeout = 1000)
    public void test_InfoCell_14() {
        String name = "InfoCell";
        assertClassFound(name);

        checkMethod(name, "getNumOfAdjacentMines", true, false, int.class);
    }

    @Test(timeout = 1000)
    public void test_InfoCell_15() {
        String name = "InfoCell";
        assertClassFound(name);

        check_InfoCell_32();
    }

    @Test(timeout = 1000)
    public void test_InfoCell_16() {
        String name = "InfoCell";
        assertClassFound(name);

        checkAllPublicMethods(name, new String[]{"getHorizontalPosition", "getVerticalPosition", "getStatus", "setStatus", "getImage", "draw", "getNumOfAdjacentMines"}, false);
    }

    //// helper functions
    private boolean allCellsNotNull(Minefield minefield, int rows, int cols) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (minefield.getCellByRowCol(i, j) == null)
                    return false;

        return true;
    }

    private boolean allCellsCovered(Minefield minefield, int rows, int cols, String cover) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Object cell = minefield.getCellByRowCol(i, j);
                if (cell == null) return false;

                String status = "";
                if (cell.getClass() == MineCell.class) {
                    status = ((MineCell) cell).getStatus();
                } else if (cell.getClass() == InfoCell.class) {
                    status = ((InfoCell) cell).getStatus();
                }
                if (!status.equals(cover)) return false;
            }
        }
        return true;
    }

    private boolean checkAdjacent(Minefield minefield, int rows, int cols) {
        // check adjacent
        // -1 means it's a mine
        final int[][] adjacentMartrix = {
                {-1, 2, 1, 0, 0},
                {2, -1, 2, 1, 0},
                {1, 2, -1, 2, 1},
                {0, 1, 2, -1, 2},
                {0, 0, 1, 2, -1},
        };
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) continue; // skip mine, only apply in this 5x5 board with 5 mines in the diagonal test case
                Object cell = minefield.getCellByRowCol(i, j);
                // cell should be infocell
                if (cell.getClass() != InfoCell.class) return false;
                if (((InfoCell) cell).getNumOfAdjacentMines() != adjacentMartrix[i][j]) return false;
            }
        }
        return true;
    }

    private void openAllInfoCells(Board board, Minefield minefield, int rows, int cols) {
        // left button on infocell -- STATUS_OPENED, openCells
        final String button = "left";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) continue; // skip mine, only apply in this 5x5 board with 5 mines in the diagonal test case
                Object cell = minefield.getCellByRowCol(i, j);
                // cell should be infocell
                if (cell.getClass() != InfoCell.class) continue;
                InfoCell ic = (InfoCell) cell;
                board.mouseClickOnLocation(ic.getHorizontalPosition(), ic.getVerticalPosition(), button);
            }
        }
    }

    private boolean checkCoordinates(Minefield minefield, int rows, int cols, int cell_size) {
        // (0,0) is a minecell
        MineCell mineCell = (MineCell) minefield.getCellByRowCol(0, 0);
        boolean hpos = mineCell.getHorizontalPosition() == 0;
        boolean vpos = mineCell.getVerticalPosition() == 0;
        // (1,1) is also a minecell
        mineCell = (MineCell) minefield.getCellByRowCol(1, 1);
        hpos &= mineCell.getHorizontalPosition() == cell_size;
        vpos &= mineCell.getVerticalPosition() == cell_size;
        // (1,2) is an infocell
        InfoCell infoCell = (InfoCell) minefield.getCellByRowCol(1, 2);
        hpos &= infoCell.getHorizontalPosition() == cell_size * 2;
        vpos &= infoCell.getVerticalPosition() == cell_size;
        // getCellByScreenCoordinates
        boolean cellScreen = minefield.getCellByScreenCoordinates(cell_size, cell_size).equals(mineCell);
        cellScreen &= minefield.getCellByScreenCoordinates(cell_size * 2, cell_size).equals(infoCell);

        return hpos && vpos && cellScreen;
    }

    private boolean leftClickInfoCell(Minefield minefield, Board board, String open) {
        // left button on infocell -- STATUS_OPENED, openCells
        // [0, 3] is an infocell, if clicked, open its 8 neighbors:[0,2],[0,4],[1,2],[1,3],[1,4]
        final String button = "left";
        int row = 0;
        int col = 3;
        InfoCell cell = (InfoCell) minefield.getCellByRowCol(row, col);
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        // check status
        final int[][] opened = {
                {0, 2},
                {0, 3},
                {0, 4},
                {1, 2},
                {1, 3},
                {1, 4}
        };
        for (int i = 0; i < opened.length; i++)
            if (!((InfoCell) minefield.getCellByRowCol(opened[i][0], opened[i][1])).getStatus().equals(open))
                return false;
        return true;
    }

    private boolean rightClickInfoCell(Minefield minefield, Board board, String cover, String mark) {
        // right button on infocell -- STATUS_MARKED/STATUS_COVERED
        // [3,0] is an infocell
        final String button = "right";
        int row = 3;
        int col = 0;
        InfoCell cell = (InfoCell) minefield.getCellByRowCol(row, col);
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        boolean statusRight = cell.getStatus().equals(mark);
        // click again
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        statusRight &= cell.getStatus().equals(cover);
        return statusRight;
    }

    private boolean rightClickMineCell(Minefield minefield, Board board, String mark, String cover) {
        // right button on minecell
        //  - 1. STATUS_COVERED --> STATUS_MARKED, remove mine
        //  - 2. STATUS_MARKED --> STATUS_COVERED, add mine
        // [0,0] is a minecell

        final String button = "right";
        int row = 0;
        int col = 0;
        MineCell cell = (MineCell) minefield.getCellByRowCol(row, col);
        // status text
        final String statusText0 = board.getStatusbar();
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        // status text should change
        final String statusText1 = board.getStatusbar();
        assertTrue("statusText should change after right clicking minecell!", !statusText0.equals(statusText1));
        // status should change to marked
        boolean statusChangeRight = cell.getStatus().equals(mark);
        // click again
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        // status text should change
        final String statusText2 = board.getStatusbar();
        assertTrue("statusText should change after right clicking minecell!", !statusText1.equals(statusText2));
        // status should change to STATUS_COVERED
        statusChangeRight &= cell.getStatus().equals(cover);
        return statusChangeRight;
    }

    private boolean leftClickMineCell(Minefield minefield, Board board, String open, String mark, String wrongmark) {
        // left button on minecell -- STATUS_OPENED, gameover
        // [1,1] is a mine cell

        // right button on info cell, test wrong mark
        // [2,1] is an infocell
        InfoCell infoCell = (InfoCell) minefield.getCellByRowCol(2, 1);
        board.mouseClickOnLocation(infoCell.getHorizontalPosition(), infoCell.getVerticalPosition(), "right");
        // infocell status should be marked(actually wrongly mark)
        assertTrue("InfoCell should change to mark after right clicking!", infoCell.getStatus().equals(mark));

        final String button = "left";
        int row = 1;
        int col = 1;
        MineCell cell = (MineCell) minefield.getCellByRowCol(row, col);
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        assertTrue("isGameOver() should return true after left clicking minecell!", board.isGameOver());
        // infocell should change to wrong mark
        assertTrue("Wrong marked infoCell should change to wrong marked when gameover!", infoCell.getStatus().equals(wrongmark));
        // status should change to open
        return cell.getStatus().equals(open);
    }

    //// Board
    private Board make_Board_5x5_5mines_in_diagonal() {
        // ensure classes exist
        assertClassFound("Board");
        assertClassFound("Configuration");
        assertClassFound("InfoCell");
        assertClassFound("MineCell");
        assertClassFound("Minefield");
        // init Configuration
        final int size = 5;
        Configuration.ROWS = size;
        Configuration.COLS = size;
        Configuration.CELL_SIZE = size;
        Configuration.MINES = size;
        Configuration.BOARD_HEIGHT = size * size + 1;
        Configuration.BOARD_WIDTH = size * size + 1;
        Configuration.STATUS_COVERED = "cover";
        Configuration.STATUS_MARKED = "mark";
        Configuration.STATUS_OPENED = "open";
        Configuration.STATUS_WRONGLY_MARKED = "wrongmark";
        // create Board
        JLabel statusbar = new JLabel("select a cell");
        Board board = new Board(Configuration.ROWS, Configuration.COLS, Configuration.MINES, statusbar);
        // change minefield, set mines in the diagonal,
        // in 5x5 board, 5 mines in the diagonal
        Minefield minefield = board.getMinefield();
        // reset minefield
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // fill with null
                minefield.setMineCell(i, j, null);
            }
            // set mine in the diagonal
            minefield.setMineCell(i, i, new MineCell(i, i));
        }
        // call method to deploy infocells
        minefield.addInfoCells();
        return board;
    }

    private void check_Board_5() {
//        5. logic test: assert setStatusbar/getStatusbar by calling just the two of them
        Board board = make_Board_5x5_5mines_in_diagonal();
        String[] text = new String[]{"This is a text", "Invalid action", "game over"};
        for (int i = 0; i < text.length; i++) {
            board.setStatusbar(text[i]);
            assertTrue("setStatusbar/getStatusbar check failed", board.getStatusbar().equals(text[i]));
        }
    }

    private void check_Board_6() {
//        6. logic test: create a small Board/minefield (e.g. 5x4) and hard-code the cells. Call mouseClickOnLocation to change the status of a cell to marked and then assert getStatusbar has correct message
        Board board = make_Board_5x5_5mines_in_diagonal();
        Minefield minefield = board.getMinefield();
        boolean result = rightClickMineCell(minefield, board, Configuration.STATUS_MARKED, Configuration.STATUS_COVERED);
        assertTrue("rightClickMineCell check failed", result);
    }

    private void check_Board_7() {
//        7. logic test: create a small Board/minefield (e.g. 5x4) and hard-code the cells. Call mouseClickOnLocation to uncover all info cells then assert that a) isGameOver is true, and b) getStatusbar message is correct
        Board board = make_Board_5x5_5mines_in_diagonal();
        Minefield minefield = board.getMinefield();
        openAllInfoCells(board, minefield, 5, 5);
        assertEquals("getStatusbar check failed", "Game over - You won!", board.getStatusbar());
    }

    private void check_Board_8() {
//        8. logic test: create a small Board/minefield (e.g. 5x4) and hard-code the cells. Call mouseClickOnLocation to uncover a mine cell then assert that a) isGameOver is true, and b) getStatusbar message is correct
        Board board = make_Board_5x5_5mines_in_diagonal();
        Minefield minefield = board.getMinefield();
        final String button = "left";
        int row = 1;
        int col = 1;
        MineCell cell = (MineCell) minefield.getCellByRowCol(row, col);
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);

        assertTrue("isGameOver check failed", board.isGameOver());
        assertEquals("getStatusbar check failed", "Game over - You lost!", board.getStatusbar());
    }

    private void check_Board_9() {
//        9. logic test: create a small Board/minefield (e.g. 5x4) and hard-code the cells. Call mouseClickOnLocation to mark to many cells. Then assert getStatusbar message is the correct (invalid action).
        Board board = make_Board_5x5_5mines_in_diagonal();
        Minefield minefield = board.getMinefield();
        final String button = "right";
        for (int i = 0; i < 5; i++) {
            MineCell cell = (MineCell) minefield.getCellByRowCol(i, i);
            board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        }
        InfoCell cell = (InfoCell) minefield.getCellByRowCol(0, 1);
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        assertEquals("getStatusbar check failed", "Invalid action", board.getStatusbar());
    }

    private void check_Board_10() {
//        10. logic test: create a small Board/minefield (e.g. 5x4) and hard-code the cells. Call mouseClickOnLocation to mark cells and assert that addMine/removeMine work correctly
        Board board = make_Board_5x5_5mines_in_diagonal();
        Minefield minefield = board.getMinefield();
        final String button = "right";
        for (int i = 0; i < 5; i++) {
            MineCell cell = (MineCell) minefield.getCellByRowCol(i, i);
            board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        }
        InfoCell cell = (InfoCell) minefield.getCellByRowCol(0, 1);
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        assertEquals("getStatusbar check failed", "Invalid action", board.getStatusbar());
        // press again
        board.mouseClickOnLocation(cell.getHorizontalPosition(), cell.getVerticalPosition(), button);
        assertEquals("getStatusbar check failed", "Invalid action", board.getStatusbar());
    }

    @Test(timeout = 1000)
    public void test_Board_01() {
        String name = "Board";
        assertClassFound(name);
        Class<?> c = getClassWhenExisting(name);
        checkConstructor(c, int.class, int.class, int.class, JLabel.class);
    }


    @Test(timeout = 1000)
    public void test_Board_02() {
        String name = "Board";
        assertClassFound(name);
        checkNoPublicFields(name, false, false);
    }


    @Test(timeout = 1000)
    public void test_Board_03() {
        String name = "Board";
        assertClassFound(name);

        checkAllPublicMethods(name, new String[]{"paintComponent", "getMinefield", "isGameOver", "setStatusbar",
                "getStatusbar", "removeMine", "addMine", "mouseClickOnLocation"}, false);
    }

    @Test(timeout = 1000)
    public void test_Board_04() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "paintComponent", true, false, void.class, Graphics.class);
    }

    @Test(timeout = 1000)
    public void test_Board_05() {
        String name = "Board";
        assertClassFound(name);
        String minefield = "Minefield";
        assertClassFound(minefield);

        checkMethod(name, "getMinefield", true, false, getClassWhenExisting(minefield));
    }

    @Test(timeout = 1000)
    public void test_Board_06() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "isGameOver", true, false, boolean.class);
    }

    @Test(timeout = 1000)
    public void test_Board_07() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "setStatusbar", true, false, void.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_Board_08() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "getStatusbar", true, false, String.class);
    }

    @Test(timeout = 1000)
    public void test_Board_09() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "removeMine", true, false, boolean.class);
    }

    @Test(timeout = 1000)
    public void test_Board_10() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "addMine", true, false, boolean.class);
    }

    @Test(timeout = 1000)
    public void test_Board_11() {
        String name = "Board";
        assertClassFound(name);

        checkMethod(name, "mouseClickOnLocation", true, false, void.class, int.class, int.class, String.class);
    }

    @Test(timeout = 1000)
    public void test_Board_12() {

        check_Board_5();
    }

    @Test(timeout = 1000)
    public void test_Board_13() {

        check_Board_6();
    }

    @Test(timeout = 1000)
    public void test_Board_14() {

        check_Board_7();
    }

    @Test(timeout = 1000)
    public void test_Board_15() {

        check_Board_8();
    }

    @Test(timeout = 1000)
    public void test_Board_16() {

        check_Board_9();
    }

    @Test(timeout = 1000)
    public void test_Board_17() {

        check_Board_10();
    }

    @Test(timeout = 1000)
    public void test_ComprehensiveFunctionality() {

        // this can be removed.

        Board board = make_Board_5x5_5mines_in_diagonal();

        Minefield minefield = board.getMinefield();
        int size = Configuration.CELL_SIZE;

        // check all cells are not null
        boolean allNotNull = allCellsNotNull(minefield, size, size);
        assertTrue("Minefield's all cells should be filled!", allNotNull);

        // check status
        boolean allCovered = allCellsCovered(minefield, size, size, Configuration.STATUS_COVERED);
        assertTrue("All the cells should be initialized with STATUS_COVERED!", allCovered);

        // check adjacent
        boolean adjacentRight = checkAdjacent(minefield, size, size);
        assertTrue("Incorrect adjacent number of mines detected!", adjacentRight);

        // check coordinates
        boolean coordinateRight = checkCoordinates(minefield, size, size, size);
        assertTrue("Incorrect getHorizontalPosition/getVerticalPosition/getCellByScreenCoordinates detected!", coordinateRight);

        //// check click event

        // check left click infocell
        boolean leftClickInfoCellRight = leftClickInfoCell(minefield, board, Configuration.STATUS_OPENED);
        assertTrue("Incorrect status detected after left clicking infocell! Maybe there's something wrong with openCells.", leftClickInfoCellRight);

        // check right click infocell
        boolean rightClickInfoCellRight = rightClickInfoCell(minefield, board, Configuration.STATUS_COVERED, Configuration.STATUS_MARKED);
        assertTrue("Incorrect status detected after right clicking infocell!", rightClickInfoCellRight);

        // check right click minecell
        boolean rightClickMineCellRight = rightClickMineCell(minefield, board, Configuration.STATUS_MARKED, Configuration.STATUS_COVERED);
        assertTrue("Incorrect status detected after right clicking minecell!", rightClickMineCellRight);

        // check left click minecell
        boolean leftClickMineCellRight = leftClickMineCell(minefield, board, Configuration.STATUS_OPENED, Configuration.STATUS_MARKED, Configuration.STATUS_WRONGLY_MARKED);
        assertTrue("Incorrect status detected after left clicking minecell!", rightClickMineCellRight);
    }

    //// The following tests ensure that submission includes all needed java files.
    // MouseReader
    @Test(timeout = 1000)
    public void test_MouseReader() {
        String name = "MouseReader";
        assertClassFound(name);
    }

    // Play
    @Test(timeout = 1000)
    public void test_Play() {
        String name = "Play";
        assertClassFound(name);
    }
}
