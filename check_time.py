import pandas as pd

def check_missing_dates(city, filename,pinyin):
    # 读取已爬取的天气数据
    df = pd.read_csv(filename)
    # 生成2022年1月1日到2024年12月31日的完整日期
    date_range = pd.date_range(start="2022-01-01", end="2024-12-31", freq="D")
    all_dates = date_range.strftime("%Y-%m-%d").tolist()  # 转换为字符串列表（不带星期几）

    # 获取爬取数据中的日期（去掉星期几）
    fetched_dates = df['日期'].str.split(' ', n=1).str[0].tolist()  # 只保留日期部分，去掉星期几

    # 查找缺失的日期
    missing_dates = list(set(all_dates) - set(fetched_dates))

    if missing_dates:
        # 按日期升序排列缺失的日期
        missing_dates.sort()

        print(f"城市 {city} 缺失的日期有：")
        for missing_date in missing_dates:
            print(f"缺失日期: {missing_date}")
        print(f"共有{len(missing_dates)}条数据")

        # 保存缺失的日期到文件
        missing_dates_df = pd.DataFrame(missing_dates, columns=["缺失日期"])
        missing_dates_df.to_csv(f"./miss_weather/{pinyin}_missing_dates.csv", index=False)
        print(f"缺失的日期已保存到 {pinyin}_missing_dates.csv")
    else:
        print(f"城市 {city} 没有缺失任何日期的数据")

# 示例：检查潍坊的数据
city = "潍坊"
pinyin = 'weifang'
filename = "./weather/weifang_weather_full.csv"  # 根据你的文件路径调整
check_missing_dates(city, filename,pinyin)
