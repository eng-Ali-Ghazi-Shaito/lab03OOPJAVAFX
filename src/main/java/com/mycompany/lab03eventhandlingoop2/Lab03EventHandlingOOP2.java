package com.mycompany.lab03eventhandlingoop2;

// AnimationTimer: class bta3mel loop 3ala kel frame (la7za) mn l screen
import javafx.animation.AnimationTimer;
// Application: l class l asesiye la ayya JavaFX program, lezim norath minna
import javafx.application.Application;
// Scene: l ma7al yale bi7ot jowweto kel l shapes (l window content)
import javafx.scene.Scene;
// Image: import l soura mn l file
import javafx.scene.image.Image;
// ImageView: bte3red l Image 3ala l screen (hiye l object yale mn2dr n7rko)
import javafx.scene.image.ImageView;
// KeyCode: bta3te esem kel zer 3al keyboard (SPACE, UP, W ...)
import javafx.scene.input.KeyCode;
// Pane: layout bi5lena n7ot l shapes 3ala ayy position mn l x w l y
import javafx.scene.layout.Pane;
// Color: la l alwen
import javafx.scene.paint.Color;
// Line: la rsm l khat
import javafx.scene.shape.Line;
// Rectangle: la rsm l mostatil (l street)
import javafx.scene.shape.Rectangle;
// Stage: l window nafsa (l etar yale bi7ot l Scene)
import javafx.stage.Stage;

// extends Application ma3neta hayda l class howe l JavaFX program taba3na
public class Lab03EventHandlingOOP2 extends Application {

    // =========================
    // CAR
    // =========================
    // 3adad l pixels yale btet7arak fiya l sayara kel ma nekbos zer (up/down/left/right)
    // yaane l 15 hiye masafe la kel kabse, mesh ser3a bel sanye
    private static final double MOVE_STEP = 15.0;
    // 3ard (width) l sayara 3al screen bel pixels
    private static final double CAR_WIDTH = 100.0;
    // tool (height) l sayara 3al screen bel pixels
    private static final double CAR_HEIGHT = 60.0;

    // sor3et l sayara lama nekbos space: 250 pixel bel sanye toward l cursor
    private static final double MOUSE_SPEED = 250.0;

    // =========================
    // STREET
    // =========================
    // maw23 l street 3al x. l origin (0,0) hiye fo2 3l shmel, fa 50 ya3ne shway 3al yamin
    private static final double STREET_X = 50.0;
    // maw23 l street 3al y. l y bezid lama nenzel la t7et, fa 220 ya3ne nezlet shway
    private static final double STREET_Y = 220.0;
    // 3ard l street (width)
    private static final double STREET_WIDTH = 1100.0;
    // tool l street (height)
    private static final double STREET_HEIGHT = 240.0;

    // =========================
    // MOUSE TARGET
    // =========================
    // l x taba3 l cursor (updated kel ma l mouse byet7arak)
    private double mouseX;
    // l y taba3 l cursor (updated kel ma l mouse byet7arak)
    private double mouseY;

    // flag (on/off): eza true ya3ne l sayara 3am temshi toward l mouse
    // btseer true bas nekbos space w l mouse jowwet l street, w btrja3 false lama tousal
    private boolean movingToMouse = false;

    // start() hiye l method yale bt3ayyet la JavaFX awwal ma yeshtghel l program
    // hon bnebne l UI kello
    @Override
    public void start(Stage stage) {
        // Pane hiye l root (l asel) yale bt7mol kel shi: street, line, car
        Pane root = new Pane();
        // lawn l background la kel l window: akhdar
        root.setStyle("-fx-background-color: #2e7d32;");

        // =========================
        // 1. STREET
        // =========================
        // create  Rectangle bi position (x,y) w 7ajm (width,height)
        Rectangle street = new Rectangle(
                STREET_X,
                STREET_Y,
                STREET_WIDTH,
                STREET_HEIGHT
        );
        // lon l rectangle background taba3 l street: rmede 3a aswad
        street.setFill(Color.web("#333333"));
        // lon  (l border): asfar
        street.setStroke(Color.YELLOW);
        // smk  l border bel pixels 
        street.setStrokeWidth(4);

        // =========================
        // 2. CENTER LINE
        // =========================
        // create  Line min nokta (startX,startY) la nokta (endX,endY)
        // hon l y nafso bel awwal w bel akher, fa l khat (horizontal) bi nos l street
        Line centerLine = new Line(
                STREET_X,
                STREET_Y + (STREET_HEIGHT / 2),
                STREET_X + STREET_WIDTH,
                STREET_Y + (STREET_HEIGHT / 2)
        );
        // lon l khat abyad
        centerLine.setStroke(Color.WHITE);
        // smk l khat
        centerLine.setStrokeWidth(3);
        // khat mkattaa3: 20 pixel khat, ba3den 15 pixel faragh, w heik
        centerLine.getStrokeDashArray().addAll(20.0, 15.0);

        // =========================
        // 3. CAR
        // =========================
        // import soura mn l resources folder (lezim tkoun mawjoude hunik)
        Image image = new Image(
                getClass().getResourceAsStream("/easy-car-drawing.jpg")
        );
        // ImageView hiye l object yale bnshufo 3al screen w mn7rko, w l Image jowweta
        ImageView car = new ImageView(image);
        // bnhaded l 7ajm yale badna yeh 3al screen
        car.setFitWidth(CAR_WIDTH);
        car.setFitHeight(CAR_HEIGHT);
        // true = bi7afez 3ala nesbet l 3ard wel tool taba3 l soura asliye
        // ya3ne l soura ma btzm lama mn3ml minimize 
        car.setPreserveRatio(true);

        // l position l awwaliye la l sayara awwal ma nshaghel l program
        // x: shway jowwa l street mn l shmel
        car.setLayoutX(STREET_X + 20);
        // y: bi nos l street bel zabet (nos l street = nos l sayara)
        car.setLayoutY(
                STREET_Y
                + (STREET_HEIGHT / 2)
                - (CAR_HEIGHT / 2)
        );

        // =========================
        // ADD OBJECTS
        // =========================
        // bnzid kel l shapes la l root. l tertib mohem : yale mnzido awwal bi bkoun wara
        // fa l street awwal, ba3den l khat, ba3den l sayara fok
        root.getChildren().addAll(
                street,
                centerLine,
                car
        );

        // =========================
        // SCENE
        // =========================
        // l Scene lezim 22l she 1 root, w l 1200 w 700 hiye l 7ajm l awwali
        Scene scene = new Scene(root, 1200, 700);

        // =====================================================
        // MOUSE EVENT
        // =====================================================
        // kel l events mrabouta bel scene. kel ma l mouse yet7arak, mnsajjel maw23o
        // (mesh bn7arrak l sayara hon, bas bnehfaz l position la nestkhdemo ba3den)
        scene.setOnMouseMoved(event -> {

            mouseX = event.getX();
            mouseY = event.getY();

        });

        // =====================================================
        // KEYBOARD EVENT
        // =====================================================
        // kel ma nekbos ayy zer 3al keyboard, hayda l code byeshtghel marra
        scene.setOnKeyPressed(event -> {

            // awwal shi mnshuf eza l zer yale nkabas howe SPACE
            if (event.getCode() == KeyCode.SPACE) {

                // eza l mouse jowwet l street, mnfa3el l flag w l sayara btblsh  temshi
                if (isMouseInsideStreet()) {

                    movingToMouse = true;

                } else {

                    // l mouse barra l street: ma mn7rk l sayara
                    movingToMouse = false;

                    System.out.println(
                            "Mouse is outside the street!"
                    );
                }

                // return ya3ne khalas, ma nkammel la ta7et (ma fi dawer lal keyboard l 3adi)
                return;
            }

            // eza mesh space, yaane zer 3adi (arrows aw WASD): ,mnrouh la function l 7araka
            handleKeyPress(event.getCode(), car);
        });

        // =====================================================
        // ANIMATION TIMER
        // =====================================================
        // l mashkele: eza 7arrakna l sayara marra wa7de bel mouse click aw key, btetn2al di8re.
        // 7ala : AnimationTimer. hiye loop btshtghel 3ala kel frame (7awale 60 marra bel sanye w hone btfro2 hsb sr3t l computer),
        // w kel marra btet7rkk l sayara shwayye zghire, fa btban 7araka ne3me.
        AnimationTimer timer = new AnimationTimer() {

            // l wa2t (bel nanoseconds) taba3 l frame yale fet. awwal shi 0 la2no ma fi frame ablo
            private long lastTime = 0;

            // handle() bit3ayyetla JavaFX automatic kel frame. mesh mnena nehna.
            // l parameter "now" = l wa2t l 7ali bel nanoseconds
            @Override
            public void handle(long now) {

                // awwal frame: ma fi frame abla la nehseb l far2, fa mnsajjel l wa2t w exit
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // deltaTime = kam sanye mar2et mn l frame l 2t3t la l frame l 7alye.
                // l nanoseconds / 1,000,000,000 = seconds. mathalan 0.016 sec (7awale 60 FPS)
                double deltaTime =
                        (now - lastTime) / 1_000_000_000.0;

                // mnhfz  l wa2t l 7ale  la ykoun howe "l frame l 2t3t " bel marra l jaye
                lastTime = now;

                // mn7rk l sayara bas eza kbasna space w l mouse jowwa l street
                if (movingToMouse) {

                    moveTowardMouse(car, deltaTime);
                }
            }
        };

        // mnshaghel l timer, mn hon l handle() betblsh tet3ayyat kel frame
        timer.start();

        // =========================
        // STAGE
        // =========================
        // esem l window
        stage.setTitle(
                "Lab 3 - Event Handling (Mouse Target)"
        );

        // mn7ot l scene jowwet l window
        stage.setScene(scene);
        // l window btefta7 maximized (kell l screen)
        stage.setMaximized(true);
        // mnshuf l window 3al screen
        stage.show();
    }

    // =========================================================
    // CHECK IF MOUSE IS INSIDE STREET
    // =========================================================
    // btraje3 true eza l cursor jowwet l rectangle (l street), w false eza barra.
    // mn2aren l x w l y taba3 l mouse ma3 7doud l street (we know l position w l size)
    private boolean isMouseInsideStreet() {

        return mouseX >= STREET_X
                && mouseX <= STREET_X + STREET_WIDTH
                && mouseY >= STREET_Y
                && mouseY <= STREET_Y + STREET_HEIGHT;
    }

    // =========================================================
    // MOVE CAR TOWARD MOUSE
    // =========================================================
    // hon l 7araka l ne3me toward l mouse. l ser3a 250 pixel/senye , bas l frame mesh dayman nafs l wa2t,
    // fa mndrob l sor3a b deltaTime: distance = speed * time. heik l ser3a btib2a sebte
    // 3ala 2yaa computer (sarii3 aw batii2)
    private void moveTowardMouse(
            ImageView car,
            double deltaTime
    ) {

        // l position l 7alye taba3 l sayara 
        double carX = car.getLayoutX();
        double carY = car.getLayoutY();

        // center l sayara (l nos), la2no badna nehseb l masefe mn l nos
        double carCenterX = carX + CAR_WIDTH / 2;
        double carCenterY = carY + CAR_HEIGHT / 2;

        // l far2 bel x w bel y bein center l sayara w l mouse
        double dx = mouseX - carCenterX;
        double dy = mouseY - carCenterY;

        // Pythagoras: l masafe l diqonal
        double distance =
                Math.sqrt(dx * dx + dy * dy);

        // eza l masafe a2al mn 2 pixel, ya3ne wselna: mnw2f l 7araka
        if (distance < 2) {

            movingToMouse = false;
            return;
        }

        // normalize: mn2som kel far2 3ala l masafe, fa bten3ata l direction bas (rakem bein -1 w 1)
        // w l ser3a ma btet8yr ken mouse areb aw b3ed
        double directionX = dx / distance;
        double directionY = dy / distance;

        // kam pixel mnemshi bhal frame: speed * time
        double movement =
                MOUSE_SPEED * deltaTime;

        // l position l jdide = l 2adime + (l ittijeh * l masefe l matloube)
        double newX =
                carX + directionX * movement;

        double newY =
                carY + directionY * movement;

        // 7doud l street yale ma lezim tetl3 barata  l sayara
        // l a2al (min) howe bidayet l street
        double minX = STREET_X;
        // l maxx mnl yamen la ma tetl3 meno  l sayara
        double maxX =
                STREET_X + STREET_WIDTH - CAR_WIDTH;

        double minY = STREET_Y;
        double maxY =
                STREET_Y + STREET_HEIGHT - CAR_HEIGHT;

        // clamp: mnhshor l ra2em bein l min w l max. Math.min bts8r l kbir, Math.max btkabber l z8ir
        double clampedX =
                Math.max(minX, Math.min(newX, maxX));

        double clampedY =
                Math.max(minY, Math.min(newY, maxY));

        // bnTabbe2 l position l jdide 3al sayara
        car.setLayoutX(clampedX);
        car.setLayoutY(clampedY);
    }

    // =========================================================
    // NORMAL KEYBOARD MOVEMENT
    // =========================================================
    // btesht8l lama nekbos arrows aw W A S D (mesh space). kel kabse btet7arak MOVE_STEP pixel
    private void handleKeyPress(
            KeyCode code,
            ImageView car
    ) {

        // mnblsh mn l position l 7alye
        double newX = car.getLayoutX();
        double newY = car.getLayoutY();

        // switch: bnshuf ayya zer nkabas w mnghayyer l x aw l y 3ala 7asabo
        switch (code) {

            // fo2: l y byen2s (la2no l origin fo2)
            case UP:
            case W:
                newY -= MOVE_STEP;
                break;

            // ta7et: l y bezid
            case DOWN:
            case S:
                newY += MOVE_STEP;
                break;

            // shmel: l x byen2s
            case LEFT:
            case A:
                newX -= MOVE_STEP;
                break;

            // yamin: l x bzid
            case RIGHT:
            case D:
                newX += MOVE_STEP;
                break;

            // ayy zer tene: ma b2sr shi
            default:
                return;
        }

        // 7oudoud l street (nafs l fekra taba3 moveTowardMouse)
        double minX = STREET_X;

        double maxX =
                STREET_X
                + STREET_WIDTH
                - CAR_WIDTH;

        double minY = STREET_Y;

        double maxY =
                STREET_Y
                + STREET_HEIGHT
                - CAR_HEIGHT;

        // mnkhalli l sayara jowwa l street (clamp)
        double clampedX =
                Math.max(minX, Math.min(newX, maxX));

        double clampedY =
                Math.max(minY, Math.min(newY, maxY));

        // bnTabbe2 l position l jdide
        car.setLayoutX(clampedX);
        car.setLayoutY(clampedY);
    }

    // =========================================================
    // MAIN
    // =========================================================
    // no2tet l dokhoul la l program. launch() btsh8l JavaFX w btesta3mel start() abel
    public static void main(String[] args) {

        Application.launch(args);
    }
}