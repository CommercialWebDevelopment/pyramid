package com.financial.pyramid.web.tree;

import com.financial.pyramid.domain.type.Position;
import com.financial.pyramid.web.form.UserForm;

/**
 * User: Danil
 * Date: 10.08.13
 * Time: 1:27
 */
public class BinaryTreeWidget {
    private static String LEFT_TREE = "left-tree";
    private static String RIGHT_TREE = "right-tree";
    private String rootElement;
    private double rootElementWidth = 0;
    private String stubTextTitle;
    private String stubTextContent;
    private String activeStatus;
    private String inactiveStatus;
    private boolean addEnabled = true;
    private static final short DEFAULT_CANVAS_HEIGHT = 30;
    private static final short MAX_CANVAS_WIDTH = 30000;
    private int depth = 1;

    public void initTree(BinaryTree tree) {
        if (tree == null) {
            this.rootElement = "";
            return;
        }
        this.rootElementWidth = calculateTotalWidth(tree);
        this.rootElement = getUserNode(tree, "user-tree");
    }

    private double calculateTotalWidth(BinaryTree tree) {
        this.depth = tree.getDepth();
        return Math.pow(2, this.depth) * 50;
    }

    private double calculateNodeWidth(BinaryTree tree) {
        return rootElementWidth / Math.pow(2, tree.getLevel()) * 2;
    }

    public String getRootElement() {
        return rootElement;
    }

    public String getUserNode(BinaryTree tree, String clazz) {
        UserForm user = tree.getValue();
        boolean both = this.addEnabled;
        String imgDir = "/resources/images/";
        String photo = user.getPhoto();
        if (photo == null) {
            photo = imgDir + (user.isActive() ? "vcard-active" : "vcard-inactive") + ".png";
        }
        String statusText = user.isActive() ? this.activeStatus : this.inactiveStatus;
        String statusColor = user.isActive() ? "green" : "red";
        String popupTitle = "<div><b>" + user.getName() + " " + user.getSurname() + "</b></div>";
        String popupContent;

        if (!user.isShowDetails()) {
            popupContent = "<small><div style=\"color:" + statusColor + "\">" + statusText + "</div></small>";
        } else {
            popupContent = "<small><div>" + user.getEmail() + "</div><div style=\"color:" + statusColor + "\">" + statusText + "</div></small>";
        }

        double width = calculateNodeWidth(tree);
        String result = "<div class='" + clazz + "' style='width:" + width + "px'>";
        result += "<div class='user-info'>";
        result += "<img src=\"" + photo + "\" class='user-photo'";

        if (!user.isShowDetails()) {
            result += "data-content='" + popupContent + "'><br>";
        } else {
            result += "title='" + popupTitle + "' data-content='" + popupContent + "'><br>";
            result += "<div class='user-name'>" + user.getName() + " " + user.getSurname() + "</div>";
        }

        result += "</div>";
        result += ((tree.isLeft() || tree.isRight() || both) ? getCanvas(width, tree) : "");
        result += "<div class='children-container'>" + getPointForUser(tree) + "</div>";
        result += "</div>";
        return result;
    }

    private String getCanvas(double width, BinaryTree user) {
        String canvas = "";
        boolean both = this.addEnabled;
        int height = (DEFAULT_CANVAS_HEIGHT + (this.depth - (user.getLevel() + 1)) * 10);
        if (width <= 200) {
            canvas = "<canvas class='user-pointer' " +
                    "drawLeft='" + (user.isLeft() || both) + "' " +
                    "drawRight='" + (user.isRight() || both) + "' " +
                    "height='" + height + "' " +
                    "width='" + width + "' " +
                    "style='height:" + height + "px; width:" + width + "px'>" +
                    "</canvas>";
        } else if (width > 200 && width < 30000) {
            canvas = "<canvas class='user-pointer' " +
                    "drawLeft='true' " +
                    "drawRight='true' " +
                    "height='" + height + "' " +
                    "width='" + width + "' " +
                    "style='height:" + height + "px; width:" + width + "px'>" +
                    "</canvas>";
        } else {
            /**
             *    Y__________________B__________________Z
             *    |                  |                  |
             *    |                / | \                |
             *    |            D /   |  \ H             |
             *    |            /|    |   \              |
             *    |          /  |    |   |\             |
             *    |        /    |    |   | \            |
             *   X|_____A/______|___ |___|__\___________|S
             *                  E    C   G   F
             *
             *
             *   tg(A) = BC/AC
             *   DE = AE ·tg(A)
             *
             *   C = 90 rad.
             * */
            double XC = width / 2; // из ромба делаем 2 - а прямоугольных треугольника
            double XA = width / 4;      // начало треугольника над первым участником, а не с 0
            double BC = height;
            double AC = XC - XA;
            double XE = MAX_CANVAS_WIDTH;
            double AE = XE - XA;
            double tgA = BC / AC;
            double DE = AE * tgA;

            if (XA > MAX_CANVAS_WIDTH) {
                double w = ((Double)(XA / MAX_CANVAS_WIDTH)).intValue() * MAX_CANVAS_WIDTH;
                canvas += "<div style='height:" + BC + "px; width:" + w + "px; float:left'></div>";
                XA -= w;
                XC -= w;
            }

            double startX = XA;
            double startY = BC;
            double endX = XE;
            double endY = BC - DE;

            if (XE < XC) {
                while (XE < XC) {
                    canvas += getCanvas(BC, MAX_CANVAS_WIDTH, startX, startY, endX, endY, true);
                    startX = 0;
                    startY = endY;

                    if ((XE + MAX_CANVAS_WIDTH) > XC) {
                        canvas += getCanvas(BC, (XC - XE), startX, startY, (XC - XE), 0, true);
                    }

                    XE += MAX_CANVAS_WIDTH;
                    AE += MAX_CANVAS_WIDTH;
                    DE = AE * tgA;
                    endY = BC - DE;
                }
            } else {
                canvas += getCanvas(BC, XC, startX, startY, XC, 0, true);
            }


            double CS = XC;
            double FS = XA;
            double CF = CS - FS;
            double CG = MAX_CANVAS_WIDTH;
            double GF = CS - CG - FS;
            if (GF > 0) {
                double HG = GF * tgA;

                startX = 0;
                startY = 0;
                endX = CG;
                endY = BC - HG;
                while (CG < CS) {
                    canvas += getCanvas(BC, MAX_CANVAS_WIDTH, startX, startY, endX, endY, true);
                    startY = endY;

                    if ((CG + MAX_CANVAS_WIDTH) > CS) {
                        canvas += getCanvas(BC, (CS - CG), startX, startY, (CS - CG), 0, false);
                    }

                    CG += MAX_CANVAS_WIDTH;
                    GF -= MAX_CANVAS_WIDTH;
                    HG = GF * tgA;
                    endY = BC - HG;
                }
            } else {
                if (CS > MAX_CANVAS_WIDTH) {
                    canvas += getCanvas(BC, MAX_CANVAS_WIDTH, 0, 0, CF, BC, true);
                    canvas += "<div style='height:" + BC + "px; width:" + (CS - MAX_CANVAS_WIDTH) + "px'></div>";
                } else {
                    canvas += getCanvas(BC, CS, 0, 0, CF, BC, false);
                }
            }
        }
        return canvas;
    }

    private String getCanvas(
            double height,
            double width,
            double startX,
            double startY,
            double endX,
            double endY,
            boolean isFloat) {
          return "<canvas class='user-pointer' " +
                  "height='" + height + "' " +
                  "width='" + width + "' " +
                  "startX='" + startX + "' " +
                  "startY='" + startY + "' " +
                  "endX='" + endX + "' " +
                  "endY='" + endY + "' " +
                  "style='height:" + height + "px; width:" + width + "px;"+ (isFloat ? "float:left" : "")+"'>" +
                  "</canvas>";
    }

    public String getStubNode(BinaryTree user, String clazz) {
        String result = "<div class='" + clazz + " stub-info user-info' ";
        result += "style='width:" + calculateNodeWidth(user) / 2 + "px'>";
        if (this.addEnabled) {
            result += "<img class='stub-node' parentId=" + user.getId();
            result += " position=" + (clazz.equals(RIGHT_TREE) ? Position.RIGHT.toString() : Position.LEFT.toString());
            result += " src='/resources/images/add-user.jpg' title='" + this.stubTextTitle + "' data-content='" + this.stubTextContent + "'>";
            result += "<br>";
        } else {
            result += "&nbsp;";
        }
        result += "</div>";
        return result;
    }

    public String getPointForUser(BinaryTree user) {
        String point = "I";
        return point + user.getId() + point;
    }

    public void addUserToWidget(BinaryTree user) {
        String result = user.isLeft() ? getUserNode(user.getLeft(), LEFT_TREE) : getStubNode(user, LEFT_TREE);
        result += user.isRight() ? getUserNode(user.getRight(), RIGHT_TREE) : getStubNode(user, RIGHT_TREE);
        rootElement = rootElement.replaceFirst(getPointForUser(user), result);
    }

    public void setStubText(String stubTextTitle, String stubTextContent) {
        this.stubTextTitle = stubTextTitle;
        this.stubTextContent = "<small>" + stubTextContent + "</small>";
    }

    public void setStatus(String activeStatus, String inactiveStatus) {
        this.activeStatus = activeStatus;
        this.inactiveStatus = inactiveStatus;
    }

    public void setAddEnabled(boolean addEnabled) {
        this.addEnabled = addEnabled;
    }
}
