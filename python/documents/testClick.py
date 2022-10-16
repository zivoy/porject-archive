import click

@click.command()
@click.option('--item', '-i', multiple=True, type=(str, int))
def putitem(item):
    for i in item:
        click.echo('name=%s id=%d' % i)

if __name__ == "__main__":
    putitem()
