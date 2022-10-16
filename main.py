import utils
import os


def main():
    file = "aa colored.png"
    # file = "sample.png"
    # file = "small.png"
    for i, mask in enumerate(utils.MakeMasks(file)):
        mask.save(os.path.join("parts", '.'.join(file.split('.')[:-1]) + "-mask-" + str(i) + ".png"))


if __name__ == "__main__":
    main()
