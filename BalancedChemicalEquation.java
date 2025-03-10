import re
import pandas
import numpy as np

def instructions():
    print("Welcome to the Balanced Chemical Equation Checker.")
    print()
    print("This program will check whether an inputted chemical equation is balanced or not.")
    print("You may input any type of chemical equation, with as many reactants and products as "
    "you prefer as long as it follows the rules below:")
    print()
    print("1. Enter the equation case-sensitively (as the element appears in the periodic table). "
    "For instance, please input Ca, and not ca for calcium.")
    print("2. Use a '+' sign between each chemical formula on each side of the equation.")
    print("3. Use a '->' sign to separate the reactants from the products")
    print("4. Use '(' and ')' to enclose polyatomic anions within a compound. For instance, (OH)2.")
    print("5. Write all subscripts and coefficients as you would write the number normally. "
    "For instance, please input 6Ca2, not 6Ca_2 nor 6Ca^2")
    print()
    print("These are some possible inputs that follow the rules mentionned above.")
    print("1. CaCO3 -> CaO + CO2 ")
    print("2. Na2SO4 + SrCl2 -> 2NaCl + SrSO4 ")
    print("3. Ca(NO3)2 + NaOH -> NaNO3 + Ca(OH)2")
    print()
    print(u'\u2500' * 10)
    print()
    

# removes the spaces that exist within items in a list
def removeSpaces(total_equation):
    return [value.strip() for value in total_equation]
    
# splits the chemical equation into compounds that are grouped into a list
# lhs stands for left hand side, while rhs stands for right hand side
def split_compound(equation):
    split_equation = equation.split(" -> ")
    global rhs
    global lhs
    lhs = split_equation[0]
    rhs = split_equation[1]
    lhs = lhs.split("+")
    rhs = rhs.split("+")
    lhs = removeSpaces(lhs)
    rhs = removeSpaces(rhs)
    return lhs, rhs
    
# using regex to split each compound in lhs and rhs into [specific element, number of the element]
def split_elements(list):
    list_element_form = []
    
    for i in list:
        # accounts for cases where the user inputs a compound that has polyatomic ions
        if "(" in i:
            # extracts any element and its number that is before the polyatomic ion
            first_half = i.split("(")[0]
            if re.search(r'\d+', first_half):
                numeric_value = int(re.search(r'\d', first_half).group())
            else:
                # if there is no number, automatically assign the numeric calue as 1
                numeric_value = 1
            list_element_form.append([first_half, numeric_value])
            
            # extracts elements and their numbers in the polyatomic ion
            numeric_value = int(i.split(")")[1])
            elements_inside_brackets = re.findall(r'([A-Z][a-z]*)(\d*)', i.split("(")[1])
            # multiples the number of an element inside the brackets by the number outside the brackets
            for (element, element_count) in elements_inside_brackets:
                modified_numeric_value = str(int(element_count) * numeric_value) if element_count else str(numeric_value)
                list_element_form.append([element, modified_numeric_value])
            
        # accounts for cases where the user inputs a compound/element wihtout polyatomic ions
        else:
            elements = re.findall(r'([A-Z][a-z]*)(\d*)', i)
            for (element, numeric_value) in elements:
                numeric_match = re.match(r'^\d*', i)
                # checks if the compound has a coefficient, and if it doesn't, it defaults to 1
                if numeric_match and numeric_match.group(0):
                    count_value = int(numeric_match.group(0))
                else:
                    count_value = 1
                # updates the number of the element by multiplying the coefficient and the subscript
                if numeric_value:
                    numeric_value = str(int(numeric_value)*count_value)
                else:
                    numeric_value = str(count_value)
                list_element_form.append([element, numeric_value])
                
    # returns a list with several nested lists that contain [specific element, number of the element]
    return list_element_form
    

# if two elements in the list are the same, it combines the number of the element to make a single nested list
def extractUniqueElements(lst):
    current_index = 0
    while current_index < len(lst):
        next_index = current_index + 1
        while next_index < len(lst):
            if lst[current_index][0] == lst[next_index][0]:
                lst[current_index][1] = str(int(lst[current_index][1]) + int(lst[next_index][1]))
                del lst[next_index]
            else:
                next_index += 1
        current_index += 1
    return lst
    

# using the pandas module, format the list in a dataframe
def PandasArrays(lhs_element_form, rhs_element_form):
    list_of_elements = []
    number_of_elements = []
    
    # make a column of the different elements that exist in the chemical equation
    for x in lhs_element_form:
        list_of_elements.append(x[0])
    side_header = ['# of Reactants', '# of Products']
    
    for (element, count1), (element, count2) in zip(lhs_element_form, rhs_element_form):
        number_of_elements.append(str(count1))
        number_of_elements.append(str(count2))
        
    # number of elements is turned into an array so it can be manipulated into a dataframe
    number_of_elements = np.array(number_of_elements).reshape(len(number_of_elements) // 2, 2)
    df = pandas.DataFrame(number_of_elements, list_of_elements, side_header)
    
    return df


def checkBalanced(list1, list2):
    list1.sort()
    list2.sort()
    if list1 == list2:
        print()
        print(PandasArrays(list1, list2))
        print()
        print("The chemical equation is balanced.")
        print()
    else:
        print()
        print(PandasArrays(list1, list2))
        print()
        print("The chemical equation is not balanced.")
        print()

def main():
    
    while True:
        equation = input("Write the equation of the chemical reaction. ")
        try:
            split_compound(equation)
            break
        except IndexError:
            print("Please read the instructions and try again.")
    
    global rhs_element_form, lhs_element_form
    
    rhs_element_form = split_elements(rhs)
    lhs_element_form = split_elements(lhs)
    rhs_element_form = extractUniqueElements(rhs_element_form)
    lhs_element_form = extractUniqueElements(lhs_element_form)
    print()
    
    # prints the dataframe and the result
    print("These are the results:")
    checkBalanced(lhs_element_form, rhs_element_form)
    
    # ask user if they want to restart the program
    # if they enter 'yes', while loop will rerun the program
    # if they enter 'no', while loop will terminate and the program will not rerun
    # if they enter something else, prompt them again
    while True:
        restartProgram = input("Please type Yes or No. Would you like to restart the program? ")
        if restartProgram.strip() == "Yes" or restartProgram.strip() == "yes" or restartProgram.strip() == "y":
            main()
        else:
            print("The program has ended.")
            print("Thank you for using the Balanced Chemical Equation Checker.")
            break
        
        
instructions()
main()