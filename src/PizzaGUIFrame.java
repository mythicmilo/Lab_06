import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame
{
    JRadioButton thinCrust, regularCrust, deepDishCrust;
    JComboBox size;
    JCheckBox cheese, pepperoni, sausage, mushroom, pepper, olive;
    JPanel mainPnl, ingredientsPnl, crustPnl, toppingsPnl, orderReceiptPnl, ctrlPnl;
    JTextArea orderTA;
    JScrollPane scroller;
    JButton orderBtn, clearBtn, quitBtn;

    String crustType = "";
    String[] pizzaSize = {"Small", "Medium", "Large", "Super"};
    double crustSizePrice;
    double toppingsTotal = 0;
    double tax = .07;
    boolean hasCheese = false;
    boolean hasPepperoni = false;
    boolean hasSausage = false;
    boolean hasMushroom = false;
    boolean hasPepper = false;
    boolean hasOlive = false;

    //Layout = gridbaglayout
    public PizzaGUIFrame()
    {
        //build the app
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        createIngredientsPanel();
        createOrderReceiptPanel();
        createControlPanel();

        setTitle("Pizza Order Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createIngredientsPanel()
    {
        //checkboxes
        ingredientsPnl = new JPanel();
        ingredientsPnl.setLayout(new GridLayout(2, 1));
        crustPnl = new JPanel();
        crustPnl.setLayout(new GridLayout(4, 1));
        toppingsPnl = new JPanel();
        toppingsPnl.setLayout(new GridLayout(3, 2));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep Dish");
        size = new JComboBox(pizzaSize);
        cheese = new JCheckBox("Cheese");
        pepperoni = new JCheckBox("Pepperoni");
        sausage = new JCheckBox("Sausage");
        mushroom = new JCheckBox("Mushrooms");
        pepper = new JCheckBox("Bell Peppers");
        olive = new JCheckBox("Olives");

        ActionListener crustListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object crust = e.getSource();

                if (crust == thinCrust)
                {
                    crustType = "Thin";
                }
                else if (crust == regularCrust)
                {
                    crustType = "Regular";
                }
                else if (crust == deepDishCrust)
                {
                    crustType = "Deep Dish";
                }
                else
                {
                    crustType = "unselected";
                }
            }
        };

        thinCrust.addActionListener(crustListener);
        regularCrust.addActionListener(crustListener);
        deepDishCrust.addActionListener(crustListener);

        ActionListener sizeListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedSize = (String) size.getSelectedItem();
                if (selectedSize.equals("Small"))
                {
                    crustSizePrice = 8.0;
                }
                else if (selectedSize.equals("Medium"))
                {
                    crustSizePrice = 12.0;
                }
                else if (selectedSize.equals("Large"))
                {
                    crustSizePrice = 16.0;
                }
                else
                {
                    crustSizePrice = 20.0;
                }
            }
        };

        size.addActionListener(sizeListener);

        ActionListener toppingListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object topping = e.getSource();

                if (topping == cheese)
                {
                    hasCheese = cheese.isSelected();
                }
                else if (topping == pepperoni)
                {
                    hasPepperoni = pepperoni.isSelected();
                }
                else if (topping == sausage)
                {
                    hasSausage = sausage.isSelected();
                }
                else if (topping == mushroom)
                {
                    hasMushroom = mushroom.isSelected();
                }
                else if (topping == pepper)
                {
                    hasPepper = pepper.isSelected();
                }
                else
                {
                    hasOlive = olive.isSelected();
                }
            }
        };

        cheese.addActionListener(toppingListener);
        pepperoni.addActionListener(toppingListener);
        sausage.addActionListener(toppingListener);
        mushroom.addActionListener(toppingListener);
        pepper.addActionListener(toppingListener);
        olive.addActionListener(toppingListener);

        crustPnl.add(thinCrust);
        crustPnl.add(regularCrust);
        crustPnl.add(deepDishCrust);
        crustPnl.add(size);
        toppingsPnl.add(cheese);
        toppingsPnl.add(mushroom);
        toppingsPnl.add(pepperoni);
        toppingsPnl.add(pepper);
        toppingsPnl.add(sausage);
        toppingsPnl.add(olive);
        ingredientsPnl.add(crustPnl);
        ingredientsPnl.add(toppingsPnl);
        mainPnl.add(ingredientsPnl, BorderLayout.CENTER);
    }

    public void createOrderReceiptPanel()
    {
        //order receipt textarea
        orderReceiptPnl = new JPanel();
        orderReceiptPnl.setLayout(new BorderLayout());
        orderTA = new JTextArea(10, 30);
        orderTA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        orderTA.setEditable(false);
        scroller = new JScrollPane(orderTA);

        orderReceiptPnl.add(scroller, BorderLayout.CENTER);
        mainPnl.add(orderReceiptPnl, BorderLayout.EAST);
    }

    public void createControlPanel()
    {
        //buttons
        ctrlPnl = new JPanel();
        orderBtn = new JButton("Order");
        orderBtn.addActionListener((ActionEvent e) -> {
            //display order in orderTA
            String endLine = "==================================================";
            String preTotal = "--------------------------------------------------";
            checkToppings();

            orderTA.setText(endLine + "\n%-10s" + crustType + "%2.2f" + crustSizePrice );
        });
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent e) -> {
            //clear orderTA
            orderTA.setText("");
        });
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent e) -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });

        ctrlPnl.add(orderBtn);
        ctrlPnl.add(clearBtn);
        ctrlPnl.add(quitBtn);
        mainPnl.add(ctrlPnl, BorderLayout.SOUTH);
    }

    private void checkToppings()
    {
        if (hasCheese)
        {
            toppingsTotal += 1.0;
        }
        if (hasPepperoni)
        {
            toppingsTotal += 1.0;
        }
        if (hasSausage)
        {
            toppingsTotal += 1.0;
        }
        if (hasMushroom)
        {
            toppingsTotal += 1.0;
        }
        if (hasPepper)
        {
            toppingsTotal += 1.0;
        }
        if (hasOlive)
        {
            toppingsTotal += 1.0;
        }
    }
}
