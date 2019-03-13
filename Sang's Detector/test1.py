def isDescending(list):
    previous = list[0]
    for number in list:
        if number > previous:
            return False
        previous = number
    return True

a = [1 , 2 , 3, 1 , 0]
print(str(isDescending(a)))
