import pandas as pd


df = pd.DataFrame({'Data': [10, 11, 30, 23, 80, 30, 32]})
writer = pd.ExcelWriter('farm_data.xlsx', engine='xlsxwriter')
i=0
sheet = 'Sheet'+ i
df.to_excel(writer, sheet_name=sheet)

workbook = writer.book
worksheet = writer.sheets[sheet]

chart = workbook.add_chart({'type': 'column'})
chart.add_series({'values': '='+sheet+'!$B$2:$B$8'})

# Insert the chart into the worksheet.
worksheet.insert_chart('D2', chart)

# Close the Pandas Excel writer and output the Excel file.
writer.save()
